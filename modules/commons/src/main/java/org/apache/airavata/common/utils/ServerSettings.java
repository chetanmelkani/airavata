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

package org.apache.airavata.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.airavata.common.exception.ApplicationSettingsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSettings extends ApplicationSettings {

	private static final Logger log = LoggerFactory.getLogger(ServerSettings.class);

    private static final String DEFAULT_USER = "default.registry.user";
    private static final String DEFAULT_USER_PASSWORD = "default.registry.password";
    private static final String DEFAULT_USER_GATEWAY = "default.registry.gateway";

    private static final String SERVER_CONTEXT_ROOT = "server.context-root";
    public static final String IP = "ip";
    // Orchestrator Constants
    public static final String ORCHESTRATOR_SERVER_HOST = "orchestrator.server.host";
    public static final String ORCHESTRATOR_SERVER_PORT = "orchestrator.server.port";
	public static final String ORCHESTRATOR_SERVER_NAME = "orchestrator.server.name";
	// Gfac constants
    public static final String GFAC_SERVER_HOST = "gfac.server.host";
    public static final String GFAC_SERVER_PORT = "gfac.server.port";
    public static final String GFAC_SERVER_NAME = "gfac.server.name";
    public static final String GFAC_THREAD_POOL_SIZE = "gfac.thread.pool.size";
	public static final int DEFAULT_GFAC_THREAD_POOL_SIZE = 50;
    public static final String GFAC_CONFIG_XML = "gfac-config.xml";
    // Credential Store constants
    public static final String CREDENTIAL_SERVER_HOST = "credential.store.server.host";
    public static final String CREDENTIAL_SERVER_PORT = "credential.store.server.port";
    // Zookeeper + curator constants
    public static final String EMBEDDED_ZK = "embedded.zk";
    public static final String ZOOKEEPER_SERVER_CONNECTION = "zookeeper.server.connection";
    public static final String ZOOKEEPER_TIMEOUT = "zookeeper.timeout";


    private static final String CREDENTIAL_STORE_DB_URL = "credential.store.jdbc.url";
    private static final String CREDENTIAL_STORE_DB_USER = "credential.store.jdbc.user";
    private static final String CREDENTIAL_STORE_DB_PASSWORD = "credential.store.jdbc.password";
    private static final String CREDENTIAL_STORE_DB_DRIVER = "credential.store.jdbc.driver";

    private static final String REGISTRY_DB_URL = "registry.jdbc.url";
    private static final String REGISTRY_DB_USER = "registry.jdbc.user";
    private static final String REGISTRY_DB_PASSWORD = "registry.jdbc.password";
    private static final String REGISTRY_DB_DRIVER = "registry.jdbc.driver";
    private static final String ENABLE_HTTPS = "enable.https";
    private static final String HOST_SCHEDULER = "host.scheduler";
    private static final String MY_PROXY_SERVER = "myproxy.server";
    private static final String MY_PROXY_USER = "myproxy.user";
    private static final String MY_PROXY_PASSWORD = "myproxy.password";
    private static final String MY_PROXY_LIFETIME = "myproxy.life";
    private static final String STATUS_PUBLISHER = "status.publisher";
    private static final String TASK_LAUNCH_PUBLISHER = "task.launch.publisher";
    private static final String ACTIVITY_LISTENERS = "activity.listeners";
    public static final String JOB_NOTIFICATION_ENABLE = "job.notification.enable";
    public static final String JOB_NOTIFICATION_EMAILIDS = "job.notification.emailids";
    public static final String JOB_NOTIFICATION_FLAGS = "job.notification.flags";
    public static final String LAUNCH_QUEUE_NAME = "launch.queue.name";
    public static final String CANCEL_QUEUE_NAME = "cancel.queue.name";


    //    Workflow Enactment Service component configuration.
    private static final String ENACTMENT_THREAD_POOL_SIZE = "enactment.thread.pool.size";
    private static final int DEFAULT_ENACTMENT_THREAD_POOL_SIZE = 10;
    private static final String WORKFLOW_PARSER = "workflow.parser";

    // email based monitoring configurations
    private static final String EMAIL_BASED_MONITORING_PERIOD = "email.based.monitoring.period";
    private static final String EMAIL_BASED_MONITOR_HOST = "email.based.monitor.host";
    private static final String EMAIL_BASED_MONITOR_ADDRESS = "email.based.monitor.address";
    private static final String EMAIL_BASED_MONITOR_PASSWORD = "email.based.monitor.password";
    private static final String EMAIL_BASED_MONITOR_FOLDER_NAME = "email.based.monitor.folder.name";
    private static final String EMAIL_BASED_MONITOR_STORE_PROTOCOL = "email.based.monitor.store.protocol";
    private static final String ENABLE_EMAIL_BASED_MONITORING = "enable.email.based.monitoring";

    private static boolean stopAllThreads = false;
    private static boolean emailBaseNotificationEnable;

    public static String getDefaultUser() throws ApplicationSettingsException {
        return getSetting(DEFAULT_USER);
    }

    public static String getLaunchQueueName() {
        return getSetting(LAUNCH_QUEUE_NAME, "launch.queue");
    }


    public static String getCancelQueueName() {
        return getSetting(CANCEL_QUEUE_NAME, "cancel.queue");
    }

    public static String getDefaultUserPassword() throws ApplicationSettingsException {
        return getSetting(DEFAULT_USER_PASSWORD);
    }

    public static String getDefaultUserGateway() throws ApplicationSettingsException {
        return getSetting(DEFAULT_USER_GATEWAY);
    }

    public static String getServerContextRoot() {
        return getSetting(SERVER_CONTEXT_ROOT, "axis2");
    }

    public static String getCredentialStoreDBUser() throws ApplicationSettingsException {
        try {
            return getSetting(CREDENTIAL_STORE_DB_USER);
        } catch (ApplicationSettingsException e) {
            return getSetting(REGISTRY_DB_USER);
        }
    }

    public static String getCredentialStoreDBPassword() throws ApplicationSettingsException {
        try {
            return getSetting(CREDENTIAL_STORE_DB_PASSWORD);
        } catch (ApplicationSettingsException e) {
            return getSetting(REGISTRY_DB_PASSWORD);
        }
    }

    public static String getCredentialStoreDBDriver() throws ApplicationSettingsException {
        try {
            return getSetting(CREDENTIAL_STORE_DB_DRIVER);
        } catch (ApplicationSettingsException e) {
            return getSetting(REGISTRY_DB_DRIVER);
        }
    }

    public static String getCredentialStoreDBURL() throws ApplicationSettingsException {
        try {
            return getSetting(CREDENTIAL_STORE_DB_URL);
        } catch (ApplicationSettingsException e) {
            return getSetting(REGISTRY_DB_URL);
        }

    }

    public static boolean isEnableHttps() {
        try {
            return Boolean.parseBoolean(getSetting(ENABLE_HTTPS));
        } catch (ApplicationSettingsException e) {
            return false;
        }
    }


    public static String getHostScheduler() throws ApplicationSettingsException {
        return getSetting(HOST_SCHEDULER);
    }

    public static boolean isStopAllThreads() {
        return stopAllThreads;
    }

    public static void setStopAllThreads(boolean stopAllThreads) {
        ServerSettings.stopAllThreads = stopAllThreads;
    }

    public static String getMyProxyServer() throws ApplicationSettingsException {
        return getSetting(MY_PROXY_SERVER);
    }

    public static String getMyProxyUser() throws ApplicationSettingsException {
        return getSetting(MY_PROXY_USER);
    }

    public static String getMyProxyPassword() throws ApplicationSettingsException {
        return getSetting(MY_PROXY_PASSWORD);
    }

    public static int getMyProxyLifetime() throws ApplicationSettingsException {
        return Integer.parseInt(getSetting(MY_PROXY_LIFETIME));
    }

    public static String[] getActivityListeners() throws ApplicationSettingsException {
        return getSetting(ACTIVITY_LISTENERS).split(",");
    }

    public static String getStatusPublisher() throws ApplicationSettingsException {
        return getSetting(STATUS_PUBLISHER);
    }

    public static String getTaskLaunchPublisher() throws ApplicationSettingsException {
        return getSetting(TASK_LAUNCH_PUBLISHER);
    }

    public static boolean isEmbeddedZK() {
        return Boolean.parseBoolean(getSetting(EMBEDDED_ZK, "true"));
    }

    public static String getIp() {
        try {
            return getSetting(IP);
        } catch (ApplicationSettingsException e) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public static int getEnactmentThreadPoolSize() {
        String threadPoolSize = null;
        try {
            threadPoolSize = getSetting(ENACTMENT_THREAD_POOL_SIZE);
        } catch (ApplicationSettingsException e) {
            return DEFAULT_ENACTMENT_THREAD_POOL_SIZE;
        }
        return Integer.valueOf(threadPoolSize);
    }

    public static String getWorkflowParser() throws ApplicationSettingsException {
        return getSetting(WORKFLOW_PARSER);
    }


    public static int getEmailMonitorPeriod() throws ApplicationSettingsException {
        return Integer.valueOf(getSetting(EMAIL_BASED_MONITORING_PERIOD, "100000"));

    }

    public static String getEmailBasedMonitorHost() throws ApplicationSettingsException {
        return getSetting(EMAIL_BASED_MONITOR_HOST);
    }

    public static String getEmailBasedMonitorAddress() throws ApplicationSettingsException {
        return getSetting(EMAIL_BASED_MONITOR_ADDRESS);
    }

    public static String getEmailBasedMonitorPassword() throws ApplicationSettingsException {
        return getSetting(EMAIL_BASED_MONITOR_PASSWORD);
    }

    public static String getEmailBasedMonitorFolderName() throws ApplicationSettingsException {
        return getSetting(EMAIL_BASED_MONITOR_FOLDER_NAME);
    }

    public static String getEmailBasedMonitorStoreProtocol() throws ApplicationSettingsException {
        return getSetting(EMAIL_BASED_MONITOR_STORE_PROTOCOL);
    }

    public static boolean isEmailBasedNotificationEnable() {
        return Boolean.valueOf(getSetting(ENABLE_EMAIL_BASED_MONITORING, "false"));
    }

    public static boolean isAPISecured() throws ApplicationSettingsException {
        return Boolean.valueOf(getSetting(Constants.IS_API_SECURED));
    }

    public static String getRemoteOauthServerUrl() throws ApplicationSettingsException {
        return getSetting(Constants.REMOTE_OAUTH_SERVER_URL);
    }

    public static String getAdminUsername() throws ApplicationSettingsException {
        return getSetting(Constants.ADMIN_USERNAME);
    }

    public static String getAdminPassword() throws ApplicationSettingsException {
        return getSetting(Constants.ADMIN_PASSWORD);
    }

    public static String getZookeeperConnection() throws ApplicationSettingsException {
        return getSetting(ZOOKEEPER_SERVER_CONNECTION, "localhost:2181");
    }

	public static int getZookeeperTimeout() {
		return Integer.valueOf(getSetting(ZOOKEEPER_TIMEOUT, "3000"));
	}

    public static String getGFacServerName() throws ApplicationSettingsException {
        return getSetting(GFAC_SERVER_NAME);
    }

    public static String getGfacServerHost() throws ApplicationSettingsException {
        return getSetting(GFAC_SERVER_HOST);
    }

    public static String getGFacServerPort() throws ApplicationSettingsException {
        return getSetting(GFAC_SERVER_PORT);
    }

    public static int getGFacThreadPoolSize() {
        try {
            String threadPoolSize = getSetting(GFAC_THREAD_POOL_SIZE);
	        if (threadPoolSize != null && !threadPoolSize.isEmpty()) {
		        return Integer.valueOf(threadPoolSize);
	        } else {
		        log.warn("Thread pool size is not configured, use default gfac thread pool size " +
				        DEFAULT_GFAC_THREAD_POOL_SIZE);
	        }
        } catch (ApplicationSettingsException e) {
	        log.warn("Couldn't read thread pool size from configuration on exception, use default gfac thread pool " +
			        "size " + DEFAULT_GFAC_THREAD_POOL_SIZE);
        }
	    return DEFAULT_GFAC_THREAD_POOL_SIZE;
    }

	public static String getOrchestratorServerName() throws ApplicationSettingsException {
		return getSetting(ORCHESTRATOR_SERVER_NAME);
	}

	public static String getOrchestratorServerHost() throws ApplicationSettingsException {
		return getSetting(ORCHESTRATOR_SERVER_HOST);
	}

	public static int getOrchestratorServerPort() throws ApplicationSettingsException {
		return Integer.valueOf(getSetting(ORCHESTRATOR_SERVER_PORT));
	}
}
