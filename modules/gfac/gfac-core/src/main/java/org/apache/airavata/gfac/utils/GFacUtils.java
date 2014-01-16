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
package org.apache.airavata.gfac.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.common.utils.StringUtil;
import org.apache.airavata.commons.gfac.type.ActualParameter;
import org.apache.airavata.gfac.Constants;
import org.apache.airavata.gfac.context.JobExecutionContext;
import org.apache.airavata.registry.api.workflow.ApplicationJob;
import org.apache.airavata.registry.api.workflow.ApplicationJob.ApplicationJobStatus;
import org.apache.airavata.schemas.gfac.BooleanArrayType;
import org.apache.airavata.schemas.gfac.BooleanParameterType;
import org.apache.airavata.schemas.gfac.DoubleArrayType;
import org.apache.airavata.schemas.gfac.DoubleParameterType;
import org.apache.airavata.schemas.gfac.FileArrayType;
import org.apache.airavata.schemas.gfac.FileParameterType;
import org.apache.airavata.schemas.gfac.FloatArrayType;
import org.apache.airavata.schemas.gfac.FloatParameterType;
import org.apache.airavata.schemas.gfac.IntegerArrayType;
import org.apache.airavata.schemas.gfac.IntegerParameterType;
import org.apache.airavata.schemas.gfac.Parameter;
import org.apache.airavata.schemas.gfac.StringArrayType;
import org.apache.airavata.schemas.gfac.StringParameterType;
import org.apache.airavata.schemas.gfac.URIArrayType;
import org.apache.airavata.schemas.gfac.URIParameterType;
import org.apache.axiom.om.OMElement;
import org.globus.gram.GramJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GFacUtils {
    private final static Logger log = LoggerFactory.getLogger(GFacUtils.class);

    private GFacUtils() {
    }

    /**
     * Read data from inputStream and convert it to String.
     *
     * @param in
     * @return String read from inputStream
     * @throws java.io.IOException
     */
    public static String readFromStream(InputStream in) throws IOException {
        try {
            StringBuffer wsdlStr = new StringBuffer();

            int read;

            byte[] buf = new byte[1024];
            while ((read = in.read(buf)) > 0) {
                wsdlStr.append(new String(buf, 0, read));
            }
            return wsdlStr.toString();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.warn("Cannot close InputStream: " + in.getClass().getName(), e);
                }
            }
        }
    }

    public static String readFileToString(String file) throws FileNotFoundException, IOException {
        BufferedReader instream = null;
        try {

            instream = new BufferedReader(new FileReader(file));
            StringBuffer buff = new StringBuffer();
            String temp = null;
            while ((temp = instream.readLine()) != null) {
                buff.append(temp);
                buff.append(Constants.NEWLINE);
            }
            return buff.toString();
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    log.warn("Cannot close FileinputStream", e);
                }
            }
        }
    }

    public static boolean isLocalHost(String appHost) throws UnknownHostException {
        String localHost = InetAddress.getLocalHost().getCanonicalHostName();
        return (localHost.equals(appHost) || Constants.LOCALHOST.equals(appHost) || Constants._127_0_0_1
                .equals(appHost));
    }

    public static String createUniqueNameForService(String serviceName) {
        String date = new Date().toString();
        date = date.replaceAll(" ", "_");
        date = date.replaceAll(":", "_");
        return serviceName + "_" + date + "_" + UUID.randomUUID();
    }

    public static URI createGsiftpURI(GridFTPContactInfo host, String localPath) throws URISyntaxException {
        StringBuffer buf = new StringBuffer();

        if (!host.hostName.startsWith("gsiftp://"))
            buf.append("gsiftp://");
        buf.append(host).append(":").append(host.port);
        if (!host.hostName.endsWith("/"))
            buf.append("/");
        buf.append(localPath);
        return new URI(buf.toString());
    }

    public static URI createGsiftpURI(String host, String localPath) throws URISyntaxException {
        StringBuffer buf = new StringBuffer();
        if (!host.startsWith("gsiftp://"))
            buf.append("gsiftp://");
        buf.append(host);
        if (!host.endsWith("/"))
            buf.append("/");
        buf.append(localPath);
        return new URI(buf.toString());
    }
    
    public static String createGsiftpURIAsString(String host, String localPath) throws URISyntaxException {
        StringBuffer buf = new StringBuffer();
        if (!host.startsWith("gsiftp://"))
            buf.append("gsiftp://");
        buf.append(host);
        if (!host.endsWith("/"))
            buf.append("/");
        buf.append(localPath);
        return buf.toString();
    }

    public static ActualParameter getInputActualParameter(Parameter parameter, OMElement element) {
        OMElement innerelement = null;
        ActualParameter actualParameter = new ActualParameter();
        if ("String".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(StringParameterType.type);
            if (!"".equals(element.getText())) {
                ((StringParameterType) actualParameter.getType()).setValue(element.getText());
            } else if (element.getChildrenWithLocalName("value").hasNext()) {
                innerelement = (OMElement) element.getChildrenWithLocalName("value").next();
                ((StringParameterType) actualParameter.getType()).setValue(innerelement.getText());
            } else {
                ((StringParameterType) actualParameter.getType()).setValue("");
            }
        } else if ("Double".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(DoubleParameterType.type);
            if (!"".equals(element.getText())) {
                ((DoubleParameterType) actualParameter.getType()).setValue(new Double(innerelement.getText()));
            } else {
                innerelement = (OMElement) element.getChildrenWithLocalName("value").next();
                ((DoubleParameterType) actualParameter.getType()).setValue(new Double(innerelement.getText()));
            }
        } else if ("Integer".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(IntegerParameterType.type);
            if (!"".equals(element.getText())) {
                ((IntegerParameterType) actualParameter.getType()).setValue(new Integer(element.getText()));
            } else {
                innerelement = (OMElement) element.getChildrenWithLocalName("value").next();
                ((IntegerParameterType) actualParameter.getType()).setValue(new Integer(innerelement.getText()));
            }
        } else if ("Float".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FloatParameterType.type);
            if (!"".equals(element.getText())) {
                ((FloatParameterType) actualParameter.getType()).setValue(new Float(element.getText()));
            } else {
                innerelement = (OMElement) element.getChildrenWithLocalName("value").next();
                ((FloatParameterType) actualParameter.getType()).setValue(new Float(innerelement.getText()));
            }
        } else if ("Boolean".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(BooleanParameterType.type);
            if (!"".equals(element.getText())) {
                ((BooleanParameterType) actualParameter.getType()).setValue(new Boolean(element.getText()));
            } else {
                innerelement = (OMElement) element.getChildrenWithLocalName("value").next();
                ((BooleanParameterType) actualParameter.getType()).setValue(Boolean.parseBoolean(innerelement.getText()));
            }
        } else if ("File".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FileParameterType.type);
            if (!"".equals(element.getText())) {
                ((FileParameterType) actualParameter.getType()).setValue(element.getText());
            } else {
                innerelement = (OMElement) element.getChildrenWithLocalName("value").next();
                ((FileParameterType) actualParameter.getType()).setValue(innerelement.getText());
            }
        } else if ("URI".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(URIParameterType.type);
            if (!"".equals(element.getText())) {
                ((URIParameterType) actualParameter.getType()).setValue(element.getText());
            } else if (element.getChildrenWithLocalName("value").hasNext()) {
                innerelement = (OMElement) element.getChildrenWithLocalName("value").next();
                System.out.println(actualParameter.getType().toString());
                log.debug(actualParameter.getType().toString());
                ((URIParameterType) actualParameter.getType()).setValue(innerelement.getText());
            } else {
                ((URIParameterType) actualParameter.getType()).setValue("");
            }
        } else if ("StringArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(StringArrayType.type);
            Iterator value = element.getChildrenWithLocalName("value");
            int i = 0;
            if (!"".equals(element.getText())) {
                String[] list = StringUtil.getElementsFromString(element.getText());
                for (String arrayValue : list) {
                    ((StringArrayType) actualParameter.getType()).insertValue(i++, arrayValue);
                }
            } else {
                while (value.hasNext()) {
                    innerelement = (OMElement) value.next();
                    ((StringArrayType) actualParameter.getType()).insertValue(i++, innerelement.getText());
                }
            }
        } else if ("DoubleArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(DoubleArrayType.type);
            Iterator value = element.getChildrenWithLocalName("value");
            int i = 0;
            if (!"".equals(element.getText())) {
                String[] list = StringUtil.getElementsFromString(element.getText());
                for (String arrayValue : list) {
                    ((DoubleArrayType) actualParameter.getType()).insertValue(i++, new Double(arrayValue));
                }
            } else {
                while (value.hasNext()) {
                    innerelement = (OMElement) value.next();
                    ((DoubleArrayType) actualParameter.getType()).insertValue(i++, new Double(innerelement.getText()));
                }
            }

        } else if ("IntegerArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(IntegerArrayType.type);
            Iterator value = element.getChildrenWithLocalName("value");
            int i = 0;
            if (!"".equals(element.getText())) {
                String[] list = StringUtil.getElementsFromString(element.getText());
                for (String arrayValue : list) {
                    ((IntegerArrayType) actualParameter.getType()).insertValue(i++, new Integer(arrayValue));
                }
            } else {
                while (value.hasNext()) {
                    innerelement = (OMElement) value.next();
                    ((IntegerArrayType) actualParameter.getType()).insertValue(i++, new Integer(innerelement.getText()));
                }
            }
        } else if ("FloatArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FloatArrayType.type);
            Iterator value = element.getChildrenWithLocalName("value");
            int i = 0;
            if (!"".equals(element.getText())) {
                String[] list = StringUtil.getElementsFromString(element.getText());
                for (String arrayValue : list) {
                    ((FloatArrayType) actualParameter.getType()).insertValue(i++, new Float(arrayValue));
                }
            } else {

                while (value.hasNext()) {
                    innerelement = (OMElement) value.next();
                    ((FloatArrayType) actualParameter.getType()).insertValue(i++, new Float(innerelement.getText()));
                }
            }
        } else if ("BooleanArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(BooleanArrayType.type);
            Iterator value = element.getChildrenWithLocalName("value");
            int i = 0;
            if (!"".equals(element.getText())) {
                String[] list = StringUtil.getElementsFromString(element.getText());
                for (String arrayValue : list) {
                    ((BooleanArrayType) actualParameter.getType()).insertValue(i++, new Boolean(arrayValue));
                }
            } else {

                while (value.hasNext()) {
                    innerelement = (OMElement) value.next();
                    ((BooleanArrayType) actualParameter.getType()).insertValue(i++, new Boolean(innerelement.getText()));
                }
            }
        } else if ("FileArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FileArrayType.type);
            Iterator value = element.getChildrenWithLocalName("value");
            int i = 0;
            if (!"".equals(element.getText())) {
                String[] list = StringUtil.getElementsFromString(element.getText());
                for (String arrayValue : list) {
                    ((FileArrayType) actualParameter.getType()).insertValue(i++, arrayValue);
                }
            } else {

                while (value.hasNext()) {
                    innerelement = (OMElement) value.next();
                    ((FileArrayType) actualParameter.getType()).insertValue(i++, innerelement.getText());
                }
            }
        } else if ("URIArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(URIArrayType.type);
            Iterator value = element.getChildrenWithLocalName("value");
            int i = 0;
            if (!"".equals(element.getText())) {
                String[] list = StringUtil.getElementsFromString(element.getText());
                for (String arrayValue : list) {
                    ((URIArrayType) actualParameter.getType()).insertValue(i++, arrayValue);
                }
            } else {

                while (value.hasNext()) {
                    innerelement = (OMElement) value.next();
                    ((URIArrayType) actualParameter.getType()).insertValue(i++, innerelement.getText());
                }
            }
        }
        return actualParameter;
    }

    public static ActualParameter getInputActualParameter(Parameter parameter, String inputVal) {
        OMElement innerelement = null;
        ActualParameter actualParameter = new ActualParameter();
        if ("String".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(StringParameterType.type);
            ((StringParameterType) actualParameter.getType()).setValue(inputVal);
        } else if ("Double".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(DoubleParameterType.type);
            ((DoubleParameterType) actualParameter.getType()).setValue(new Double(inputVal));
        } else if ("Integer".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(IntegerParameterType.type);
            ((IntegerParameterType) actualParameter.getType()).setValue(new Integer(inputVal));
        } else if ("Float".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FloatParameterType.type);
            ((FloatParameterType) actualParameter.getType()).setValue(new Float(inputVal));
        } else if ("Boolean".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(BooleanParameterType.type);
            ((BooleanParameterType) actualParameter.getType()).setValue(new Boolean(inputVal));
        } else if ("File".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FileParameterType.type);
            ((FileParameterType) actualParameter.getType()).setValue(inputVal);
        } else if ("URI".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(URIParameterType.type);
            ((URIParameterType) actualParameter.getType()).setValue(inputVal);
        } else if ("StringArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(StringArrayType.type);
            Iterator iterator = Arrays.asList(StringUtil.getElementsFromString(inputVal)).iterator();
            int i = 0;
            while (iterator.hasNext()) {
                innerelement = (OMElement) iterator.next();
                ((StringArrayType) actualParameter.getType()).insertValue(i++, innerelement.getText());
            }
        } else if ("DoubleArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(DoubleArrayType.type);
            Iterator value = Arrays.asList(StringUtil.getElementsFromString(inputVal)).iterator();
            int i = 0;
            while (value.hasNext()) {
                innerelement = (OMElement) value.next();
                ((DoubleArrayType) actualParameter.getType()).insertValue(i++, new Double(innerelement.getText()));
            }
        } else if ("IntegerArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(IntegerArrayType.type);
            Iterator value = Arrays.asList(StringUtil.getElementsFromString(inputVal)).iterator();
            int i = 0;
            while (value.hasNext()) {
                innerelement = (OMElement) value.next();
                ((IntegerArrayType) actualParameter.getType()).insertValue(i++, new Integer(innerelement.getText()));
            }
        } else if ("FloatArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FloatArrayType.type);
            Iterator value = Arrays.asList(StringUtil.getElementsFromString(inputVal)).iterator();
            int i = 0;
            while (value.hasNext()) {
                innerelement = (OMElement) value.next();
                ((FloatArrayType) actualParameter.getType()).insertValue(i++, new Float(innerelement.getText()));
            }
        } else if ("BooleanArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(BooleanArrayType.type);
            Iterator value = Arrays.asList(StringUtil.getElementsFromString(inputVal)).iterator();
            int i = 0;
            while (value.hasNext()) {
                innerelement = (OMElement) value.next();
                ((BooleanArrayType) actualParameter.getType()).insertValue(i++, new Boolean(innerelement.getText()));
            }
        } else if ("FileArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(FileArrayType.type);
            Iterator value = Arrays.asList(StringUtil.getElementsFromString(inputVal)).iterator();
            int i = 0;
            while (value.hasNext()) {
                innerelement = (OMElement) value.next();
                ((FileArrayType) actualParameter.getType()).insertValue(i++, innerelement.getText());
            }
        } else if ("URIArray".equals(parameter.getParameterType().getName())) {
            actualParameter = new ActualParameter(URIArrayType.type);
            Iterator value = Arrays.asList(StringUtil.getElementsFromString(inputVal)).iterator();
            int i = 0;
            while (value.hasNext()) {
                innerelement = (OMElement) value.next();
                ((URIArrayType) actualParameter.getType()).insertValue(i++, innerelement.getText());
            }
        }
        return actualParameter;
    }
    

	public static ApplicationJob createApplicationJob(
			JobExecutionContext jobExecutionContext) {
		ApplicationJob appJob = new ApplicationJob();
		appJob.setExperimentId((String) jobExecutionContext.getProperty(Constants.PROP_TOPIC));
		appJob.setWorkflowExecutionId(appJob.getExperimentId());
		appJob.setNodeId((String)jobExecutionContext.getProperty(Constants.PROP_WORKFLOW_NODE_ID));
		appJob.setServiceDescriptionId(jobExecutionContext.getApplicationContext().getServiceDescription().getType().getName());
		appJob.setHostDescriptionId(jobExecutionContext.getApplicationContext().getHostDescription().getType().getHostName());
		appJob.setApplicationDescriptionId(jobExecutionContext.getApplicationContext().getApplicationDeploymentDescription().getType().getApplicationName().getStringValue());
		return appJob;
	}

    public static void updateApplicationJobStatusUpdateTime(JobExecutionContext context, String jobId, Date statusUpdateTime) {
        AiravataAPI airavataAPI = context.getGFacConfiguration().getAiravataAPI();
        try {
            airavataAPI.getProvenanceManager().updateApplicationJobStatusUpdateTime(jobId, statusUpdateTime);
        } catch (AiravataAPIInvocationException e) {
            log.error("Error in updating application job status time " + statusUpdateTime.toString() + " for job Id " + jobId + "!!!", e);
        }
    }

    public static void updateApplicationJobStatus(JobExecutionContext context, String jobId, ApplicationJobStatus status, Date statusUpdateTime) {
        AiravataAPI airavataAPI = context.getGFacConfiguration().getAiravataAPI();
        try {
            airavataAPI.getProvenanceManager().updateApplicationJobStatus(jobId, status, statusUpdateTime);
        } catch (AiravataAPIInvocationException e) {
            log.error("Error in updating application job status " + status.toString() + " for job Id " + jobId + "!!!", e);
        }
    }

    /**
     * Gets the job ids given experiment id.
     *
     * @param context      The job execution context.
     * @param experimentId The experiment id.
     * @return List of job ids relevant to given experiment id.
     */
    public static List<ApplicationJob> getJobIds(JobExecutionContext context, String experimentId) {

        AiravataAPI airavataAPI = context.getGFacConfiguration().getAiravataAPI();
        try {
            return airavataAPI.getProvenanceManager().getApplicationJobs(experimentId, null, null);
        } catch (AiravataAPIInvocationException e) {
            log.error("Error retrieving application jobs for experiment id " + experimentId, e);
        }

        return new ArrayList<ApplicationJob>(0);
    }

    /**
     * Gets the job ids given experiment id and workflow id.
     *
     * @param context      The job execution context.
     * @param experimentId The experiment id.
     * @param workflowId   The workflow id
     * @return List of job ids relevant to given experiment id and workflow id.
     */
    public static List<ApplicationJob> getJobIds(JobExecutionContext context, String experimentId, String workflowId) {

        AiravataAPI airavataAPI = context.getGFacConfiguration().getAiravataAPI();
        try {
            return airavataAPI.getProvenanceManager().getApplicationJobs(experimentId, workflowId, null);
        } catch (AiravataAPIInvocationException e) {
            log.error("Error retrieving application jobs for experiment id " + experimentId, " workflow id " +
                    workflowId, e);
        }

        return new ArrayList<ApplicationJob>(0);
    }

    /**
     * Gets the job ids given experiment id and workflow id.
     *
     * @param context      The job execution context.
     * @param experimentId The experiment id.
     * @param workflowId   The workflow id
     * @return List of job ids relevant to given experiment id and workflow id.
     */
    public static List<ApplicationJob> getJobIds(JobExecutionContext context, String experimentId,
                                                 String workflowId, String nodeId) {

        AiravataAPI airavataAPI = context.getGFacConfiguration().getAiravataAPI();
        try {
            return airavataAPI.getProvenanceManager().getApplicationJobs(experimentId, workflowId, nodeId);
        } catch (AiravataAPIInvocationException e) {
            log.error("Error retrieving application jobs for experiment id " + experimentId, " workflow id " +
                    workflowId, e);
        }

        return new ArrayList<ApplicationJob>(0);
    }

    /*public static RequestData getRequestData(Properties configurationProperties) {

        RequestData requestData = new RequestData();

        requestData.setMyProxyServerUrl(configurationProperties.getProperty(Constants.MYPROXY_SERVER));
        requestData.setMyProxyUserName(configurationProperties.getProperty(Constants.MYPROXY_USER));
        requestData.setMyProxyPassword(configurationProperties.getProperty(Constants.MYPROXY_PASS));

        int lifeTime;
        String sLife = configurationProperties.getProperty(Constants.MYPROXY_LIFE);
        if (sLife != null) {
            lifeTime = Integer.parseInt(sLife);
            requestData.setMyProxyLifeTime(lifeTime);
        } else {
            log.info("The configuration does not specify a default life time");
        }



    }
*/


    public static void recordApplicationJob(JobExecutionContext context, ApplicationJob job) {
        AiravataAPI airavataAPI = context.getGFacConfiguration().getAiravataAPI();
        try {
            airavataAPI.getProvenanceManager().addApplicationJob(job);
        } catch (AiravataAPIInvocationException e) {
            log.error("Error in persisting application job data for application job " + job.getJobId() + "!!!", e);
        }
    }

    public static void updateApplicationJobStatus(JobExecutionContext context, String jobId, ApplicationJobStatus status) {
        updateApplicationJobStatus(context, jobId, status, Calendar.getInstance().getTime());
    }

    public static ApplicationJobStatus getApplicationJobStatus(int gramStatus){
        switch(gramStatus){
            case GramJob.STATUS_UNSUBMITTED:
                return ApplicationJobStatus.UN_SUBMITTED;
            case GramJob.STATUS_ACTIVE:
                return ApplicationJobStatus.EXECUTING;
            case GramJob.STATUS_DONE:
                return ApplicationJobStatus.FINISHED;
            case GramJob.STATUS_FAILED:
                return ApplicationJobStatus.FAILED;
            case GramJob.STATUS_PENDING:
                return ApplicationJobStatus.PENDING;
            case GramJob.STATUS_STAGE_IN:
                return ApplicationJobStatus.INITIALIZE;
            case GramJob.STATUS_STAGE_OUT:
                return ApplicationJobStatus.FINALIZE;
            case GramJob.STATUS_SUSPENDED:
                return ApplicationJobStatus.SUSPENDED;
            default:
                return ApplicationJobStatus.UNKNOWN;
        }
    }
}