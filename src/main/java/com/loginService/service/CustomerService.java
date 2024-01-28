package com.loginService.service;

import com.loginService.dto.ResponseModel;

public interface CustomerService {
    public ResponseModel getAllCustomer();
    public ResponseModel getCustomerById(String id);
}
