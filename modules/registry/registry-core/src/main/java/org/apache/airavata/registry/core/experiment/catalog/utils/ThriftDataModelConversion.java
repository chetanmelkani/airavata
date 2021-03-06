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

package org.apache.airavata.registry.core.experiment.catalog.utils;

import org.apache.airavata.model.application.io.DataType;
import org.apache.airavata.model.application.io.InputDataObjectType;
import org.apache.airavata.model.application.io.OutputDataObjectType;
import org.apache.airavata.model.commons.ErrorModel;
import org.apache.airavata.model.experiment.ExperimentModel;
import org.apache.airavata.model.experiment.ExperimentSummaryModel;
import org.apache.airavata.model.experiment.UserConfigurationDataModel;
import org.apache.airavata.model.job.JobModel;
import org.apache.airavata.model.process.ProcessModel;
import org.apache.airavata.model.scheduling.ComputationalResourceSchedulingModel;
import org.apache.airavata.model.status.*;
import org.apache.airavata.model.task.TaskModel;
import org.apache.airavata.model.task.TaskTypes;
import org.apache.airavata.model.workspace.Gateway;
import org.apache.airavata.model.workspace.Project;
import org.apache.airavata.registry.core.experiment.catalog.ExperimentCatResource;
import org.apache.airavata.registry.core.experiment.catalog.resources.*;
import org.apache.airavata.registry.cpi.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThriftDataModelConversion {
    private final static Logger logger = LoggerFactory.getLogger(ThriftDataModelConversion.class);

    public static Project getProject (ProjectResource pr) throws RegistryException {
        if (pr != null) {
            Project project = new Project();
            project.setProjectID(pr.getId());
            project.setName(pr.getName());
            if (pr.getCreationTime()!=null) {
				project.setCreationTime(pr.getCreationTime().getTime());
			}
			project.setDescription(pr.getDescription());
            project.setOwner(pr.getWorker().getUser());
            List<ProjectUserResource> projectUserList = pr.getProjectUserList();
            List<String> sharedUsers = new ArrayList<String>();
            if (projectUserList != null && !projectUserList.isEmpty()){
                for (ProjectUserResource resource : projectUserList){
                    sharedUsers.add(resource.getUserName());
                }
            }
            project.setSharedUsers(sharedUsers);
            return project;
        }
        return null;
    }

    public static Gateway getGateway (GatewayResource resource){
        Gateway gateway = new Gateway();
        gateway.setGatewayId(resource.getGatewayId());
        gateway.setGatewayName(resource.getGatewayName());
        gateway.setDomain(resource.getDomain());
        gateway.setEmailAddress(resource.getEmailAddress());
        return gateway;
    }

    public static List<Gateway> getAllGateways (List<ExperimentCatResource> gatewayList){
        List<Gateway> gateways = new ArrayList<Gateway>();
        for (ExperimentCatResource resource : gatewayList){
            gateways.add(getGateway((GatewayResource)resource));
        }
        return gateways;
    }

    public static ExperimentSummaryModel getExperimentSummary(ExperimentSummaryResource experimentSummaryResource) throws RegistryException {
        if (experimentSummaryResource != null){
            ExperimentSummaryModel experimentSummary = new ExperimentSummaryModel();
            experimentSummary.setProjectId(experimentSummaryResource.getProjectId());
            experimentSummary.setExperimentId(experimentSummaryResource.getExperimentId());
            experimentSummary.setGatewayId(experimentSummaryResource.getGatewayId());
            experimentSummary.setCreationTime(experimentSummaryResource.getCreationTime().getTime());
            experimentSummary.setUserName(experimentSummaryResource.getUserName());
            experimentSummary.setName(experimentSummaryResource.getExperimentName());
            experimentSummary.setDescription(experimentSummaryResource.getDescription());
            ExperimentState experimentState = ExperimentState.valueOf(experimentSummaryResource.getState());
//            FIXME : Experiment Summary resource is incomplete at this point
//            experimentSummary.setExperimentStatus(experimentSummaryResource.getState());
            return experimentSummary;
        }
        return null;
    }


    public static ExperimentModel getExperiment(ExperimentResource experimentResource) throws RegistryException {
        if (experimentResource != null){
            ExperimentModel experiment = new ExperimentModel();
            experiment.setProjectId(experimentResource.getProjectId());
            experiment.setExperimentId(experimentResource.getExperimentId());
            experiment.setGatewayId(experimentResource.getGatewayId());
            experiment.setCreationTime(experimentResource.getCreationTime().getTime());
            experiment.setUserName(experimentResource.getUserName());
            experiment.setExperimentName(experimentResource.getExperimentName());
            experiment.setDescription(experimentResource.getDescription());
            experiment.setEnableEmailNotification(experimentResource.getEnableEmailNotification());
            experiment.setGatewayExecutionId(experimentResource.getGatewayExecutionId());
            if (experiment.isEnableEmailNotification()){
                String notificationEmails = experimentResource.getEmailAddresses();
                experiment.setEmailAddresses(getEmailAddresses(notificationEmails.split(",")));
            }
            List<ExperimentInputResource> experimentInputs = experimentResource.getExperimentInputs();
            experiment.setExperimentInputs(getExpInputs(experimentInputs));
            List<ExperimentOutputResource> experimentOutputs = experimentResource.getExperimentOutputs();
            experiment.setExperimentOutputs(getExpOutputs(experimentOutputs));
            ExperimentStatusResource experimentStatus = experimentResource.getExperimentStatus();
            if (experimentStatus != null){
                experiment.setExperimentStatus(getExperimentStatus(experimentStatus));
            }
            List<ExperimentErrorResource> errorDetails = experimentResource.getExperimentErrors();
            if (errorDetails!= null && !errorDetails.isEmpty()){
                experiment.setErrors(getExperimentErrorList(errorDetails));
            }
            UserConfigurationDataResource userConfigurationDataResource
                    = experimentResource.getUserConfigurationDataResource();
            if(userConfigurationDataResource != null){
                experiment.setUserConfigurationData(getUserConfigData(userConfigurationDataResource));
            }
            return experiment;
        }
        return null;
    }

    public static InputDataObjectType getInput(Object object){
        if (object != null){
            InputDataObjectType dataObjectType = new InputDataObjectType();
            if (object instanceof ExperimentInputResource){
                ExperimentInputResource inputResource = (ExperimentInputResource) object;
                dataObjectType.setName(inputResource.getInputName());
                dataObjectType.setValue(inputResource.getInputValue());
                dataObjectType.setType(DataType.valueOf(inputResource.getDataType()));
                dataObjectType.setApplicationArgument(inputResource.getApplicationArgument());
                dataObjectType.setStandardInput(inputResource.getStandardInput());
                dataObjectType.setUserFriendlyDescription(inputResource.getUserFriendlyDescription());
                dataObjectType.setMetaData(inputResource.getMetadata());
                dataObjectType.setInputOrder(inputResource.getInputOrder());
                dataObjectType.setIsRequired(inputResource.getIsRequired());
                dataObjectType.setRequiredToAddedToCommandLine(inputResource.getRequiredToAddedToCmd());
                dataObjectType.setDataStaged(inputResource.getDataStaged());
                return dataObjectType;
            }else if (object instanceof ProcessInputResource){
                ProcessInputResource inputResource = (ProcessInputResource)object;
                dataObjectType.setName(inputResource.getInputName());
                dataObjectType.setValue(inputResource.getInputValue());
                dataObjectType.setType(DataType.valueOf(inputResource.getDataType()));
                dataObjectType.setApplicationArgument(inputResource.getApplicationArgument());
                dataObjectType.setStandardInput(inputResource.getStandardInput());
                dataObjectType.setUserFriendlyDescription(inputResource.getUserFriendlyDescription());
                dataObjectType.setMetaData(inputResource.getMetadata());
                dataObjectType.setInputOrder(inputResource.getInputOrder());
                dataObjectType.setIsRequired(inputResource.getIsRequired());
                dataObjectType.setRequiredToAddedToCommandLine(inputResource.getRequiredToAddedToCmd());
                dataObjectType.setDataStaged(inputResource.getDataStaged());
                return dataObjectType;
            }else {
                return null;
            }
        }
        return null;
    }

    public static OutputDataObjectType getOutput(Object object){
        if (object != null){
            OutputDataObjectType dataObjectType = new OutputDataObjectType();
            if (object instanceof ExperimentOutputResource){
                ExperimentOutputResource outputResource = (ExperimentOutputResource)object;
                dataObjectType.setName(outputResource.getOutputName());
                dataObjectType.setValue(outputResource.getOutputValue());
                dataObjectType.setType(DataType.valueOf(outputResource.getDataType()));
                dataObjectType.setApplicationArgument(outputResource.getApplicationArgument());
                dataObjectType.setIsRequired(outputResource.getIsRequired());
                dataObjectType.setRequiredToAddedToCommandLine(outputResource.getRequiredToAddedToCmd());
                dataObjectType.setDataMovement(outputResource.getDataMovement());
                dataObjectType.setLocation(outputResource.getLocation());
                dataObjectType.setSearchQuery(outputResource.getSearchQuery());
                return dataObjectType;
            }else if (object instanceof ProcessOutputResource) {
                ProcessOutputResource outputResource = (ProcessOutputResource) object;
                dataObjectType.setName(outputResource.getOutputName());
                dataObjectType.setValue(outputResource.getOutputValue());
                dataObjectType.setType(DataType.valueOf(outputResource.getDataType()));
                dataObjectType.setApplicationArgument(outputResource.getApplicationArgument());
                dataObjectType.setIsRequired(outputResource.getIsRequired());
                dataObjectType.setRequiredToAddedToCommandLine(outputResource.getRequiredToAddedToCmd());
                dataObjectType.setDataMovement(outputResource.getDataMovement());
                dataObjectType.setLocation(outputResource.getLocation());
                dataObjectType.setSearchQuery(outputResource.getSearchQuery());
                return dataObjectType;
            } else {
                return null;
            }
        }
        return null;
    }

    public static List<String> getEmailAddresses (String[] resourceList){
        List<String> emailAddresses = new ArrayList<String>();
            for (String email : resourceList){
                emailAddresses.add(email);
            }
        return emailAddresses;
    }

    public static List<InputDataObjectType> getExpInputs (List<ExperimentInputResource> exInputList){
        List<InputDataObjectType> expInputs = new ArrayList<InputDataObjectType>();
        if (exInputList != null && !exInputList.isEmpty()){
            for (ExperimentInputResource inputResource : exInputList){
                InputDataObjectType exInput = getInput(inputResource);
                expInputs.add(exInput);
            }
        }
        return expInputs;
    }

    public static List<OutputDataObjectType> getExpOutputs (List<ExperimentOutputResource> experimentOutputResourceList){
        List<OutputDataObjectType> exOutputs = new ArrayList<OutputDataObjectType>();
        if (experimentOutputResourceList != null && !experimentOutputResourceList.isEmpty()){
            for (ExperimentOutputResource outputResource : experimentOutputResourceList){
                OutputDataObjectType output = getOutput(outputResource);
                exOutputs.add(output);
            }
        }
        return exOutputs;
    }

    public static List<InputDataObjectType> getProcessInputs (List<ProcessInputResource> processInputResources){
        List<InputDataObjectType> nodeInputs = new ArrayList<InputDataObjectType>();
        if (processInputResources != null && !processInputResources.isEmpty()){
            for (ProcessInputResource inputResource : processInputResources){
                InputDataObjectType nodeInput = getInput(inputResource);
                nodeInputs.add(nodeInput);
            }
        }
        return nodeInputs;
    }

    public static List<OutputDataObjectType> getProcessOutputs (List<ProcessOutputResource> processOutputResources){
        List<OutputDataObjectType> processOutputs = new ArrayList<OutputDataObjectType>();
        if (processOutputResources != null && !processOutputResources.isEmpty()){
            for (ProcessOutputResource outputResource : processOutputResources){
                OutputDataObjectType output = getOutput(outputResource);
                processOutputs.add(output);
            }
        }
        return processOutputs;
    }

    public static ExperimentStatus getExperimentStatus(ExperimentStatusResource status){
        if (status != null){
            ExperimentStatus experimentStatus = new ExperimentStatus();
            if (status.getState() == null || status.getState().equals("")){
                status.setState("UNKNOWN");
            }
            experimentStatus.setState(ExperimentState.valueOf(status.getState()));
            experimentStatus.setTimeOfStateChange(status.getTimeOfStateChange().getTime());
            experimentStatus.setReason(status.getReason());
            return experimentStatus;
        }
        return null;
    }

    public static ProcessStatus getProcessStatus (ProcessStatusResource status){
        if (status != null){
            ProcessStatus processStatus = new ProcessStatus();
            if (status.getState() == null || status.getState().equals("")){
                status.setState("UNKNOWN");
            }
            processStatus.setState(ProcessState.valueOf(status.getState()));
            processStatus.setTimeOfStateChange(status.getTimeOfStateChange().getTime());
            processStatus.setReason(status.getReason());
            return processStatus;
        }
        return null;
    }

    public static TaskStatus getTaskStatus (TaskStatusResource status){
        if (status != null){
            TaskStatus taskStatus = new TaskStatus();
            if (status.getState() == null || status.getState().equals("")){
                status.setState("UNKNOWN");
            }
            taskStatus.setState(TaskState.valueOf(status.getState()));
            taskStatus.setTimeOfStateChange(status.getTimeOfStateChange().getTime());
            taskStatus.setReason(status.getReason());
            return taskStatus;
        }
        return null;
    }

    public static JobStatus getJobStatus (JobStatusResource status){
        if (status != null){
            JobStatus jobStatus = new JobStatus();
            if (status.getState() == null || status.getState().equals("")){
                status.setState("UNKNOWN");
            }
            jobStatus.setJobState(JobState.valueOf(status.getState()));
            jobStatus.setTimeOfStateChange(status.getTimeOfStateChange().getTime());
            jobStatus.setReason(status.getReason());
            return jobStatus;
        }
        return null;
    }

    public static ProcessModel getProcesModel (ProcessResource processResource) throws RegistryException {
        if (processResource != null){
            ProcessModel processModel = new ProcessModel();
            processModel.setProcessId(processResource.getProcessId());
            processModel.setExperimentId(processResource.getExperimentId());
            processModel.setCreationTime(processResource.getCreationTime().getTime());
            processModel.setLastUpdateTime(processResource.getLastUpdateTime().getTime());
            processModel.setProcessDetail(processResource.getProcessDetail());
            processModel.setApplicationInterfaceId(processResource.getApplicationInterfaceId());
            processModel.setTaskDag(processResource.getTaskDag());

            processModel.setProcessInputs(getProcessInputs(processResource.getProcessInputs()));
            processModel.setProcessOutputs(getProcessOutputs(processResource.getProcessOutputs()));

            processModel.setProcessError(getErrorModel(processResource.getProcessError()));
            processModel.setProcessStatus(getProcessStatus(processResource.getProcessStatus()));

            processModel.setResourceSchedule(getProcessResourceSchedule(processResource.getProcessResourceSchedule()));

            processModel.setTasks(getTaskModelList(processResource.getTaskList()));

            return processModel;
        }
        return null;
    }

    public static List<TaskModel> getTaskModelList (List<TaskResource> resources) throws RegistryException {
        List<TaskModel> taskDetailsList = new ArrayList<TaskModel>();
        if (resources != null && !resources.isEmpty()){
            for (TaskResource resource : resources){
                taskDetailsList.add(getTaskModel(resource));
            }
        }
        return taskDetailsList;
    }

    public static TaskModel getTaskModel (TaskResource taskResource) throws RegistryException {
        TaskModel model = new TaskModel();
        model.setTaskId(taskResource.getTaskId());
        model.setTaskType(TaskTypes.valueOf(taskResource.getTaskType()));
        model.setParentProcessId(taskResource.getParentProcessId());
        model.setCreationTime(taskResource.getCreationTime().getTime());
        model.setLastUpdateTime(taskResource.getLastUpdateTime().getTime());
        model.setTaskDetail(taskResource.getTaskDetail());
        model.setTaskInternalStore(taskResource.getTaskInternalStore());

        model.setTaskStatus(getTaskStatus(taskResource.getTaskStatus()));
        model.setTaskError(getErrorModel(taskResource.getTaskError()));

        return model;
    }

    public static JobModel getJobModel (JobResource jobResource) throws RegistryException {
        JobModel model = new JobModel();
        model.setJobId(jobResource.getJobId());
        model.setTaskId(jobResource.getTaskId());
        model.setJobDescription(jobResource.getJobDescription());
        model.setCreationTime(jobResource.getCreationTime().getTime());
        model.setComputeResourceConsumed(jobResource.getComputeResourceConsumed());
        model.setJobName(jobResource.getJobName());
        model.setWorkingDir(jobResource.getWorkingDir());
        model.setJobStatus(getJobStatus(jobResource.getJobStatus()));

        return model;
    }


    public static ErrorModel getErrorModel (Object object){
        if (object != null) {
            ErrorModel errorModel = new ErrorModel();
            if (object instanceof ExperimentErrorResource) {
                ExperimentErrorResource errorResource = (ExperimentErrorResource) object;
                errorModel.setErrorId(errorResource.getErrorId());
                errorModel.setCreationTime(errorResource.getCreationTime().getTime());
                errorModel.setActualErrorMessage(errorResource.getActualErrorMessage());
                errorModel.setUserFriendlyMessage(errorResource.getUserFriendlyMessage());
                errorModel.setTransientOrPersistent(errorResource.getTransientOrPersistent());
                errorModel.setRootCauseErrorIdList(Arrays.asList(errorResource.getRootCauseErrorIdList().split(",")));
                return errorModel;
            } else if (object instanceof ProcessOutputResource) {
                ProcessErrorResource errorResource = (ProcessErrorResource) object;
                errorModel.setErrorId(errorResource.getErrorId());
                errorModel.setCreationTime(errorResource.getCreationTime().getTime());
                errorModel.setActualErrorMessage(errorResource.getActualErrorMessage());
                errorModel.setUserFriendlyMessage(errorResource.getUserFriendlyMessage());
                errorModel.setTransientOrPersistent(errorResource.getTransientOrPersistent());
                errorModel.setRootCauseErrorIdList(Arrays.asList(errorResource.getRootCauseErrorIdList().split(",")));
                return errorModel;
            } else if (object instanceof TaskErrorResource) {
                TaskErrorResource errorResource = (TaskErrorResource) object;
                errorModel.setErrorId(errorResource.getErrorId());
                errorModel.setCreationTime(errorResource.getCreationTime().getTime());
                errorModel.setActualErrorMessage(errorResource.getActualErrorMessage());
                errorModel.setUserFriendlyMessage(errorResource.getUserFriendlyMessage());
                errorModel.setTransientOrPersistent(errorResource.getTransientOrPersistent());
                errorModel.setRootCauseErrorIdList(Arrays.asList(errorResource.getRootCauseErrorIdList().split(",")));
                return errorModel;
            } else {
                return null;
            }
        }
        return null;
    }

    public static List<ErrorModel> getExperimentErrorList(List<ExperimentErrorResource> errorResources){
        List<ErrorModel> errorList = new ArrayList<ErrorModel>();
        if (errorResources != null && !errorResources.isEmpty()){
            for (ExperimentErrorResource errorResource : errorResources){
                errorList.add(getErrorModel(errorResource));
            }
        }
        return errorList;
    }

    public static UserConfigurationDataModel getUserConfigData (UserConfigurationDataResource resource) throws RegistryException {
        if (resource != null){
            UserConfigurationDataModel data = new UserConfigurationDataModel();
            data.setAiravataAutoSchedule(resource.getAiravataAutoSchedule());
            data.setOverrideManualScheduledParams(resource.getOverrideManualScheduledParams());
            data.setShareExperimentPublicly(resource.getShareExperimentPublically());
            data.setUserDN(resource.getUserDn());
            data.setGenerateCert(resource.getGenerateCert());

            ComputationalResourceSchedulingModel resourceSchedulingModel = new ComputationalResourceSchedulingModel();
            resourceSchedulingModel.setResourceHostId(resource.getResourceHostId());
            resourceSchedulingModel.setTotalCPUCount(resource.getTotalCpuCount());
            resourceSchedulingModel.setNodeCount(resource.getNodeCount());
            resourceSchedulingModel.setNumberOfThreads(resource.getNumberOfThreads());
            resourceSchedulingModel.setQueueName(resource.getQueueName());
            resourceSchedulingModel.setWallTimeLimit(resource.getWallTimeLimit());
            resourceSchedulingModel.setTotalPhysicalMemory(resource.getTotalPhysicalMemory());
            data.setComputationalResourceScheduling(resourceSchedulingModel);

            return data;
        }
        return null;
    }

    public static ComputationalResourceSchedulingModel getProcessResourceSchedule (ProcessResourceScheduleResource resource){
        if (resource != null){
            ComputationalResourceSchedulingModel resourceSchedulingModel = new ComputationalResourceSchedulingModel();
            resourceSchedulingModel.setResourceHostId(resource.getResourceHostId());
            resourceSchedulingModel.setTotalCPUCount(resource.getTotalCpuCount());
            resourceSchedulingModel.setNodeCount(resource.getNodeCount());
            resourceSchedulingModel.setNumberOfThreads(resource.getNumberOfThreads());
            resourceSchedulingModel.setQueueName(resource.getQueueName());
            resourceSchedulingModel.setWallTimeLimit(resource.getWallTimeLimit());
            resourceSchedulingModel.setTotalPhysicalMemory(resource.getTotalPhysicalMemory());
            return resourceSchedulingModel;
        }
        return null;
    }

    public static ComputationalResourceSchedulingModel getComputationalResourceScheduling(UserConfigurationDataResource resource) {
        if (resource != null){
            ComputationalResourceSchedulingModel resourceSchedulingModel = new ComputationalResourceSchedulingModel();
            resourceSchedulingModel.setResourceHostId(resource.getResourceHostId());
            resourceSchedulingModel.setTotalCPUCount(resource.getTotalCpuCount());
            resourceSchedulingModel.setNodeCount(resource.getNodeCount());
            resourceSchedulingModel.setNumberOfThreads(resource.getNumberOfThreads());
            resourceSchedulingModel.setQueueName(resource.getQueueName());
            resourceSchedulingModel.setWallTimeLimit(resource.getWallTimeLimit());
            resourceSchedulingModel.setTotalPhysicalMemory(resource.getTotalPhysicalMemory());
            return resourceSchedulingModel;
        }
        return null;
    }
}