/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.airavata.model.error;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * A list of Airavata API Error Message Types
 * 
 *  UNKNOWN: No information available about the error
 *   
 *  PERMISSION_DENIED: Not permitted to perform action
 * 
 *  INTERNAL_ERROR: Unexpected problem with the service
 * 
 *  AUTHENTICATION_FAILURE: The client failed to authenticate.
 * 
 *  INVALID_AUTHORIZATION: Security Token and/or Username and/or password is incorrect
 *   
 *  AUTHORIZATION_EXPIRED: Authentication token expired
 *  
 *  UNKNOWN_GATEWAY_ID: The gateway is not registered with Airavata.
 * 
 *  UNSUPPORTED_OPERATION: Operation denied because it is currently unsupported.
 */
@SuppressWarnings("all") public enum AiravataErrorType implements org.apache.thrift.TEnum {
  UNKNOWN(0),
  PERMISSION_DENIED(1),
  INTERNAL_ERROR(2),
  AUTHENTICATION_FAILURE(3),
  INVALID_AUTHORIZATION(4),
  AUTHORIZATION_EXPIRED(5),
  UNKNOWN_GATEWAY_ID(6),
  UNSUPPORTED_OPERATION(7);

  private final int value;

  private AiravataErrorType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static AiravataErrorType findByValue(int value) { 
    switch (value) {
      case 0:
        return UNKNOWN;
      case 1:
        return PERMISSION_DENIED;
      case 2:
        return INTERNAL_ERROR;
      case 3:
        return AUTHENTICATION_FAILURE;
      case 4:
        return INVALID_AUTHORIZATION;
      case 5:
        return AUTHORIZATION_EXPIRED;
      case 6:
        return UNKNOWN_GATEWAY_ID;
      case 7:
        return UNSUPPORTED_OPERATION;
      default:
        return null;
    }
  }
}
