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
#include "airavataErrors_types.h"

#include <algorithm>

namespace apache { namespace airavata { namespace api { namespace error {

int _kAiravataErrorTypeValues[] = {
  AiravataErrorType::UNKNOWN,
  AiravataErrorType::PERMISSION_DENIED,
  AiravataErrorType::INTERNAL_ERROR,
  AiravataErrorType::AUTHENTICATION_FAILURE,
  AiravataErrorType::INVALID_AUTHORIZATION,
  AiravataErrorType::AUTHORIZATION_EXPIRED,
  AiravataErrorType::UNKNOWN_GATEWAY_ID,
  AiravataErrorType::UNSUPPORTED_OPERATION
};
const char* _kAiravataErrorTypeNames[] = {
  "UNKNOWN",
  "PERMISSION_DENIED",
  "INTERNAL_ERROR",
  "AUTHENTICATION_FAILURE",
  "INVALID_AUTHORIZATION",
  "AUTHORIZATION_EXPIRED",
  "UNKNOWN_GATEWAY_ID",
  "UNSUPPORTED_OPERATION"
};
const std::map<int, const char*> _AiravataErrorType_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(8, _kAiravataErrorTypeValues, _kAiravataErrorTypeNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));

const char* ExperimentNotFoundException::ascii_fingerprint = "EFB929595D312AC8F305D5A794CFEDA1";
const uint8_t ExperimentNotFoundException::binary_fingerprint[16] = {0xEF,0xB9,0x29,0x59,0x5D,0x31,0x2A,0xC8,0xF3,0x05,0xD5,0xA7,0x94,0xCF,0xED,0xA1};

uint32_t ExperimentNotFoundException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_message = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->message);
          isset_message = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_message)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t ExperimentNotFoundException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("ExperimentNotFoundException");

  xfer += oprot->writeFieldBegin("message", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->message);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(ExperimentNotFoundException &a, ExperimentNotFoundException &b) {
  using ::std::swap;
  swap(a.message, b.message);
}

const char* ProjectNotFoundException::ascii_fingerprint = "EFB929595D312AC8F305D5A794CFEDA1";
const uint8_t ProjectNotFoundException::binary_fingerprint[16] = {0xEF,0xB9,0x29,0x59,0x5D,0x31,0x2A,0xC8,0xF3,0x05,0xD5,0xA7,0x94,0xCF,0xED,0xA1};

uint32_t ProjectNotFoundException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_message = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->message);
          isset_message = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_message)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t ProjectNotFoundException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("ProjectNotFoundException");

  xfer += oprot->writeFieldBegin("message", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->message);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(ProjectNotFoundException &a, ProjectNotFoundException &b) {
  using ::std::swap;
  swap(a.message, b.message);
}

const char* InvalidRequestException::ascii_fingerprint = "EFB929595D312AC8F305D5A794CFEDA1";
const uint8_t InvalidRequestException::binary_fingerprint[16] = {0xEF,0xB9,0x29,0x59,0x5D,0x31,0x2A,0xC8,0xF3,0x05,0xD5,0xA7,0x94,0xCF,0xED,0xA1};

uint32_t InvalidRequestException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_message = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->message);
          isset_message = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_message)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t InvalidRequestException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("InvalidRequestException");

  xfer += oprot->writeFieldBegin("message", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->message);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(InvalidRequestException &a, InvalidRequestException &b) {
  using ::std::swap;
  swap(a.message, b.message);
}

const char* TimedOutException::ascii_fingerprint = "99914B932BD37A50B983C5E7C90AE93B";
const uint8_t TimedOutException::binary_fingerprint[16] = {0x99,0x91,0x4B,0x93,0x2B,0xD3,0x7A,0x50,0xB9,0x83,0xC5,0xE7,0xC9,0x0A,0xE9,0x3B};

uint32_t TimedOutException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;


  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    xfer += iprot->skip(ftype);
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  return xfer;
}

uint32_t TimedOutException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("TimedOutException");

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(TimedOutException &a, TimedOutException &b) {
  using ::std::swap;
  (void) a;
  (void) b;
}

const char* AuthenticationException::ascii_fingerprint = "EFB929595D312AC8F305D5A794CFEDA1";
const uint8_t AuthenticationException::binary_fingerprint[16] = {0xEF,0xB9,0x29,0x59,0x5D,0x31,0x2A,0xC8,0xF3,0x05,0xD5,0xA7,0x94,0xCF,0xED,0xA1};

uint32_t AuthenticationException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_message = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->message);
          isset_message = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_message)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t AuthenticationException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("AuthenticationException");

  xfer += oprot->writeFieldBegin("message", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->message);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(AuthenticationException &a, AuthenticationException &b) {
  using ::std::swap;
  swap(a.message, b.message);
}

const char* AuthorizationException::ascii_fingerprint = "EFB929595D312AC8F305D5A794CFEDA1";
const uint8_t AuthorizationException::binary_fingerprint[16] = {0xEF,0xB9,0x29,0x59,0x5D,0x31,0x2A,0xC8,0xF3,0x05,0xD5,0xA7,0x94,0xCF,0xED,0xA1};

uint32_t AuthorizationException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_message = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->message);
          isset_message = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_message)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t AuthorizationException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("AuthorizationException");

  xfer += oprot->writeFieldBegin("message", ::apache::thrift::protocol::T_STRING, 1);
  xfer += oprot->writeString(this->message);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(AuthorizationException &a, AuthorizationException &b) {
  using ::std::swap;
  swap(a.message, b.message);
}

const char* AiravataClientException::ascii_fingerprint = "24652790C81ECE22B629CB60A19F1E93";
const uint8_t AiravataClientException::binary_fingerprint[16] = {0x24,0x65,0x27,0x90,0xC8,0x1E,0xCE,0x22,0xB6,0x29,0xCB,0x60,0xA1,0x9F,0x1E,0x93};

uint32_t AiravataClientException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_airavataErrorType = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast0;
          xfer += iprot->readI32(ecast0);
          this->airavataErrorType = (AiravataErrorType::type)ecast0;
          isset_airavataErrorType = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->parameter);
          this->__isset.parameter = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_airavataErrorType)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t AiravataClientException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("AiravataClientException");

  xfer += oprot->writeFieldBegin("airavataErrorType", ::apache::thrift::protocol::T_I32, 1);
  xfer += oprot->writeI32((int32_t)this->airavataErrorType);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.parameter) {
    xfer += oprot->writeFieldBegin("parameter", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->parameter);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(AiravataClientException &a, AiravataClientException &b) {
  using ::std::swap;
  swap(a.airavataErrorType, b.airavataErrorType);
  swap(a.parameter, b.parameter);
  swap(a.__isset, b.__isset);
}

const char* ValidatorResult::ascii_fingerprint = "EB04A806CFFC9025AEE48CFFDC378A86";
const uint8_t ValidatorResult::binary_fingerprint[16] = {0xEB,0x04,0xA8,0x06,0xCF,0xFC,0x90,0x25,0xAE,0xE4,0x8C,0xFF,0xDC,0x37,0x8A,0x86};

uint32_t ValidatorResult::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_result = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_BOOL) {
          xfer += iprot->readBool(this->result);
          isset_result = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->errorDetails);
          this->__isset.errorDetails = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_result)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t ValidatorResult::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("ValidatorResult");

  xfer += oprot->writeFieldBegin("result", ::apache::thrift::protocol::T_BOOL, 1);
  xfer += oprot->writeBool(this->result);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.errorDetails) {
    xfer += oprot->writeFieldBegin("errorDetails", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->errorDetails);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(ValidatorResult &a, ValidatorResult &b) {
  using ::std::swap;
  swap(a.result, b.result);
  swap(a.errorDetails, b.errorDetails);
  swap(a.__isset, b.__isset);
}

const char* ValidationResults::ascii_fingerprint = "E73BC8630EE405DA5FB801ED852143D2";
const uint8_t ValidationResults::binary_fingerprint[16] = {0xE7,0x3B,0xC8,0x63,0x0E,0xE4,0x05,0xDA,0x5F,0xB8,0x01,0xED,0x85,0x21,0x43,0xD2};

uint32_t ValidationResults::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_validationState = false;
  bool isset_validationResultList = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_BOOL) {
          xfer += iprot->readBool(this->validationState);
          isset_validationState = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_LIST) {
          {
            this->validationResultList.clear();
            uint32_t _size1;
            ::apache::thrift::protocol::TType _etype4;
            xfer += iprot->readListBegin(_etype4, _size1);
            this->validationResultList.resize(_size1);
            uint32_t _i5;
            for (_i5 = 0; _i5 < _size1; ++_i5)
            {
              xfer += this->validationResultList[_i5].read(iprot);
            }
            xfer += iprot->readListEnd();
          }
          isset_validationResultList = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_validationState)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  if (!isset_validationResultList)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t ValidationResults::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("ValidationResults");

  xfer += oprot->writeFieldBegin("validationState", ::apache::thrift::protocol::T_BOOL, 1);
  xfer += oprot->writeBool(this->validationState);
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldBegin("validationResultList", ::apache::thrift::protocol::T_LIST, 2);
  {
    xfer += oprot->writeListBegin(::apache::thrift::protocol::T_STRUCT, static_cast<uint32_t>(this->validationResultList.size()));
    std::vector<ValidatorResult> ::const_iterator _iter6;
    for (_iter6 = this->validationResultList.begin(); _iter6 != this->validationResultList.end(); ++_iter6)
    {
      xfer += (*_iter6).write(oprot);
    }
    xfer += oprot->writeListEnd();
  }
  xfer += oprot->writeFieldEnd();

  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(ValidationResults &a, ValidationResults &b) {
  using ::std::swap;
  swap(a.validationState, b.validationState);
  swap(a.validationResultList, b.validationResultList);
}

const char* LaunchValidationException::ascii_fingerprint = "99E9D28CC9613B8567277FD2B86021FA";
const uint8_t LaunchValidationException::binary_fingerprint[16] = {0x99,0xE9,0xD2,0x8C,0xC9,0x61,0x3B,0x85,0x67,0x27,0x7F,0xD2,0xB8,0x60,0x21,0xFA};

uint32_t LaunchValidationException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_validationResult = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_STRUCT) {
          xfer += this->validationResult.read(iprot);
          isset_validationResult = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->errorMessage);
          this->__isset.errorMessage = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_validationResult)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t LaunchValidationException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("LaunchValidationException");

  xfer += oprot->writeFieldBegin("validationResult", ::apache::thrift::protocol::T_STRUCT, 1);
  xfer += this->validationResult.write(oprot);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.errorMessage) {
    xfer += oprot->writeFieldBegin("errorMessage", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->errorMessage);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(LaunchValidationException &a, LaunchValidationException &b) {
  using ::std::swap;
  swap(a.validationResult, b.validationResult);
  swap(a.errorMessage, b.errorMessage);
  swap(a.__isset, b.__isset);
}

const char* AiravataSystemException::ascii_fingerprint = "24652790C81ECE22B629CB60A19F1E93";
const uint8_t AiravataSystemException::binary_fingerprint[16] = {0x24,0x65,0x27,0x90,0xC8,0x1E,0xCE,0x22,0xB6,0x29,0xCB,0x60,0xA1,0x9F,0x1E,0x93};

uint32_t AiravataSystemException::read(::apache::thrift::protocol::TProtocol* iprot) {

  uint32_t xfer = 0;
  std::string fname;
  ::apache::thrift::protocol::TType ftype;
  int16_t fid;

  xfer += iprot->readStructBegin(fname);

  using ::apache::thrift::protocol::TProtocolException;

  bool isset_airavataErrorType = false;

  while (true)
  {
    xfer += iprot->readFieldBegin(fname, ftype, fid);
    if (ftype == ::apache::thrift::protocol::T_STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
        if (ftype == ::apache::thrift::protocol::T_I32) {
          int32_t ecast7;
          xfer += iprot->readI32(ecast7);
          this->airavataErrorType = (AiravataErrorType::type)ecast7;
          isset_airavataErrorType = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      case 2:
        if (ftype == ::apache::thrift::protocol::T_STRING) {
          xfer += iprot->readString(this->message);
          this->__isset.message = true;
        } else {
          xfer += iprot->skip(ftype);
        }
        break;
      default:
        xfer += iprot->skip(ftype);
        break;
    }
    xfer += iprot->readFieldEnd();
  }

  xfer += iprot->readStructEnd();

  if (!isset_airavataErrorType)
    throw TProtocolException(TProtocolException::INVALID_DATA);
  return xfer;
}

uint32_t AiravataSystemException::write(::apache::thrift::protocol::TProtocol* oprot) const {
  uint32_t xfer = 0;
  xfer += oprot->writeStructBegin("AiravataSystemException");

  xfer += oprot->writeFieldBegin("airavataErrorType", ::apache::thrift::protocol::T_I32, 1);
  xfer += oprot->writeI32((int32_t)this->airavataErrorType);
  xfer += oprot->writeFieldEnd();

  if (this->__isset.message) {
    xfer += oprot->writeFieldBegin("message", ::apache::thrift::protocol::T_STRING, 2);
    xfer += oprot->writeString(this->message);
    xfer += oprot->writeFieldEnd();
  }
  xfer += oprot->writeFieldStop();
  xfer += oprot->writeStructEnd();
  return xfer;
}

void swap(AiravataSystemException &a, AiravataSystemException &b) {
  using ::std::swap;
  swap(a.airavataErrorType, b.airavataErrorType);
  swap(a.message, b.message);
  swap(a.__isset, b.__isset);
}

}}}} // namespace
