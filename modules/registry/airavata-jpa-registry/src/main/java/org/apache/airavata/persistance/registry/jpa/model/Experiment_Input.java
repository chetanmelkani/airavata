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

package org.apache.airavata.persistance.registry.jpa.model;

import org.apache.openjpa.persistence.DataCache;

import javax.persistence.*;
import java.io.Serializable;

@DataCache
@Entity
@Table(name ="EXPERIMENT_INPUT")
@IdClass(Experiment_Input_PK.class)
public class Experiment_Input implements Serializable {
    @Id
    @Column(name = "EXPERIMENT_ID")
    private String experiment_id;

    @Id
    @Column(name = "INPUT_KEY")
    private String ex_key;

    @Lob
    @Column(name = "VALUE")
    private char[] value;

    @Column(name = "DATA_TYPE")
    private String dataType;

    @Column(name = "APP_ARGUMENT")
    private String appArgument;

    @Column(name = "STANDARD_INPUT")
    private boolean standardInput;

    @Column(name = "USER_FRIENDLY_DESC")
    private String userFriendlyDesc;

    @Column(name = "METADATA")
    private String metadata;

    @ManyToOne
    @JoinColumn(name = "EXPERIMENT_ID")
    private Experiment experiment;

    public String getExperiment_id() {
        return experiment_id;
    }

    public void setExperiment_id(String experiment_id) {
        this.experiment_id = experiment_id;
    }

    public String getEx_key() {
        return ex_key;
    }

    public void setEx_key(String ex_key) {
        this.ex_key = ex_key;
    }

    public char[] getValue() {
        return value;
    }

    public void setValue(char[] value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public String getAppArgument() {
        return appArgument;
    }

    public void setAppArgument(String appArgument) {
        this.appArgument = appArgument;
    }

    public boolean isStandardInput() {
        return standardInput;
    }

    public void setStandardInput(boolean standardInput) {
        this.standardInput = standardInput;
    }

    public String getUserFriendlyDesc() {
        return userFriendlyDesc;
    }

    public void setUserFriendlyDesc(String userFriendlyDesc) {
        this.userFriendlyDesc = userFriendlyDesc;
    }
}
