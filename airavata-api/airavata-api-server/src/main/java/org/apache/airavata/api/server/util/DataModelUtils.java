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

package org.apache.airavata.api.server.util;

import org.apache.airavata.registry.core.experiment.catalog.impl.RegistryFactory;
import org.apache.airavata.registry.cpi.AppCatalogException;
import org.apache.airavata.registry.cpi.ApplicationInterface;
import org.apache.airavata.common.utils.ServerSettings;
import org.apache.airavata.model.util.ExecutionType;
import org.apache.airavata.model.experiment.ExperimentModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DataModelUtils {
    private static final Logger logger = LoggerFactory.getLogger(DataModelUtils.class);
	public static ExecutionType getExecutionType(ExperimentModel experiment) throws Exception{
		try {
			ApplicationInterface applicationInterface = RegistryFactory.getAppCatalog().getApplicationInterface();
			List<String> allApplicationInterfaceIds = applicationInterface.getAllApplicationInterfaceIds();
			String applicationId = experiment.getExecutionId();
			if (allApplicationInterfaceIds.contains(applicationId)){
				return ExecutionType.SINGLE_APP;
			} else {
				List<String> allWorkflows = RegistryFactory.getAppCatalog().getWorkflowCatalog().getAllWorkflows(ServerSettings.getDefaultUserGateway());
				if (allWorkflows.contains(applicationId)){
					return ExecutionType.WORKFLOW;
				}
			}
		} catch (AppCatalogException e) {
            logger.error("Error while retrieving execution type for experiment : " + experiment.getExperimentId(), e);
            throw new Exception("Error while retrieving execution type for experiment : " + experiment.getExperimentId(), e);
		}
		return ExecutionType.UNKNOWN;
	}
}
