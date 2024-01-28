package com.loginService.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@lombok.Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {
    private CustomerRegistrationDetailsDto customerRegistrationDetailsDto;
    private CustomerSuccessLoginDetailsDto customerSuccessLoginDetailsDto;
    private List<CustomerDataDto> customerDetailsList;
    private CustomerDataDto customerDataDto;
}
