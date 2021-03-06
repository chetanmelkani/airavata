/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

include "airavata_commons.thrift"
include "status_models.thrift"

namespace java org.apache.airavata.model.task
namespace php Airavata.Model.Task
namespace cpp apache.airavata.model.task
namespace py apache.airavata.model.task

/**
 * TaskTypes: An enumerated list of TaskTypes. Task being generic, the task type will provide the concrete interpretation.
 *
*/
enum TaskTypes {
    ENV_SETUP,
    DATA_STAGING,
    JOB_SUBMISSION,
    ENV_CLEANUP,
    MONITORING
}

/**
 * TaskModel: A structure holding the generic task details.
 *
 * taskDetail:
 *   A friendly description of the task, usally used to communicate information to users.
 *
 * taskInternalStore:
 *   A generic byte object for the Task developer to store internal serialized data into registry catalogs.
*/
struct TaskModel {
    1: required string taskId = airavata_commons.DEFAULT_ID,
    2: required TaskTypes taskType,
    3: required string parentProcessId,
    4: required i64 creationTime,
    5: required i64 lastUpdateTime,
    6: required status_models.TaskStatus taskStatus,
    7: optional string taskDetail,
    8: optional byte taskInternalStore,
    9: optional airavata_commons.ErrorModel taskError
}

/**
 * DataStagingTaskModel: A structure holding the data staging task details.
 *
 * Source and Destination locations includes standard representation of protocol, host, port and path
 *   A friendly description of the task, usally used to communicate information to users.
 *
*/
struct DataStagingTaskModel {
    1: required string source,
    2: required string destination,
    3: optional i64 transferStartTime,
    4: optional i64 transferEndTime,
    5: optional string transferRate
}