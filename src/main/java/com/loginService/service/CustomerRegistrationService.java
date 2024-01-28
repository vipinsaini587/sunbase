package com.loginService.service;

import com.loginService.dto.CustomerDataDto;
import com.loginService.dto.ResponseModel;
import com.loginService.dto.CustomerRegistrationDetailsDto;

import java.util.List;

public interface CustomerRegistrationService {
    public ResponseModel registerCustomer(CustomerRegistrationDetailsDto customerRegistrationDetailsDto);
    public ResponseModel updateCustomer(CustomerDataDto customerDataDto);
    public ResponseModel deleteCustomer(String id);

    ResponseModel syncCustomer(String token);
}
