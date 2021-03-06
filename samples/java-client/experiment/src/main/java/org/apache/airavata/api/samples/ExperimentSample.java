/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.airavata.api.samples;

import java.util.ArrayList;
import java.util.List;

import org.apache.airavata.api.Airavata;
import org.apache.airavata.api.Airavata.Client;
import org.apache.airavata.api.client.AiravataClientFactory;
import org.apache.airavata.client.AiravataAPIFactory;
import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.client.tools.DocumentCreatorNew;
import org.apache.airavata.common.exception.ApplicationSettingsException;
import org.apache.airavata.common.utils.ClientSettings;
import org.apache.airavata.model.error.AiravataClientConnectException;
import org.apache.airavata.model.error.AiravataClientException;
import org.apache.airavata.model.error.AiravataSystemException;
import org.apache.airavata.model.error.ExperimentNotFoundException;
import org.apache.airavata.model.error.InvalidRequestException;
import org.apache.airavata.model.util.ExperimentModelUtil;
import org.apache.airavata.model.workspace.Project;
import org.apache.airavata.model.experiment.ComputationalResourceScheduling;
import org.apache.airavata.model.experiment.DataObjectType;
import org.apache.airavata.model.experiment.DataType;
import org.apache.airavata.model.experiment.ExperimentModel;
import org.apache.airavata.model.experiment.UserConfigurationData;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExperimentSample {
    private static final Logger log = LoggerFactory.getLogger(ExperimentSample.class);

    protected static AiravataAPI getAiravataAPI() throws AiravataAPIInvocationException {
        return  AiravataAPIFactory.getAPI("default", "admin");
    }
	
	protected static Airavata.Client getClient() throws Exception {
        String THRIFT_SERVER_HOST = ClientSettings.getSetting("thrift.server.host");
        int THRIFT_SERVER_PORT = Integer.parseInt(ClientSettings.getSetting("thrift.server.port"));
        return AiravataClientFactory.createAiravataClient(THRIFT_SERVER_HOST, THRIFT_SERVER_PORT);

    }
	public static void main(String[] args) throws Exception {
	AiravataAPI airavataAPI = getAiravataAPI();
	DocumentCreatorNew documentCreatorNew = new DocumentCreatorNew(RegistryFactory.getAppCatalog());
        documentCreatorNew.createLocalHostDocs();
        String user = "admin";

        Project project=new Project();
        project.setName("project1");
        project.setProjectID("project1");
		project.setOwner(user);
        Client client = getClient();
		String projectId=client.createProject(project);
		System.out.println("Created new project '"+project.getName()+"' with project id "+projectId);
        List<DataObjectType> exInputs = new ArrayList<DataObjectType>();
        DataObjectType input = new DataObjectType();
        input.setKey("echo_input");
        input.setType(DataType.STRING);
        input.setValue("echo_output=Hello World");
        exInputs.add(input);

        List<DataObjectType> exOut = new ArrayList<DataObjectType>();
        DataObjectType output = new DataObjectType();
        output.setKey("echo_output");
        output.setType(DataType.STDOUT);
        output.setValue("");
        exOut.add(output);

        Experiment simpleExperiment =
                ExperimentModelUtil.createSimpleExperiment(projectId, user, "echoExperiment", "SimpleEcho0", "SimpleEcho0", exInputs);
        simpleExperiment.setExperimentOutputs(exOut);

        ComputationalResourceScheduling scheduling = ExperimentModelUtil.createComputationResourceScheduling("localhost", 1, 1, 1, "normal", 0, 0, 1, "sds128");
        scheduling.setResourceHostId("localhost");
        UserConfigurationData userConfigurationData = new UserConfigurationData();
        userConfigurationData.setAiravataAutoSchedule(false);
        userConfigurationData.setOverrideManualScheduledParams(false);
        userConfigurationData.setComputationalResourceScheduling(scheduling);
        simpleExperiment.setUserConfigurationData(userConfigurationData);



		final String expId = client.createExperiment(simpleExperiment);
        System.out.println("Experiment Id returned : " + expId);


        client.launchExperiment(expId,"testToken");

        System.out.println("Launched successfully");

	}
}
