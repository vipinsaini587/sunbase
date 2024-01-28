package com.loginService.service;

import com.loginService.dto.ResponseModel;
import com.loginService.dto.CustomerLoginDetailsDto;

public interface CustomerLoginService {

    String apiCall(CustomerLoginDetailsDto customerLoginDetailsDto);
}
