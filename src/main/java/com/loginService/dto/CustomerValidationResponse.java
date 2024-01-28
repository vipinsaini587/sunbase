package com.loginService.dto;

import com.loginService.common.Constants;
import lombok.Data;

@Data
public class CustomerValidationResponse {
    private String status = "false";
    private String message = Constants.TECHNICAL_ERROR;
}
