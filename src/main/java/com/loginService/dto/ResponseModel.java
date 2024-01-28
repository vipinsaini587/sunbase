package com.loginService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.loginService.common.Constants;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseModel {
    private String statusCode = Constants.failureCode;
    private String status = Constants.failureStatus;
    private String message;
    private Data data;
}
