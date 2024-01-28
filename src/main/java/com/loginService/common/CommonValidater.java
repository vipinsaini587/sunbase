package com.loginService.common;

import com.loginService.dto.CustomerRegistrationDetailsDto;
import com.loginService.dto.CustomerValidationResponse;

public class CommonValidater {
    public static CustomerValidationResponse validateCustomerRegistration(CustomerRegistrationDetailsDto customerRegistrationDetailsDto){
        CustomerValidationResponse customerValidationResponse = new CustomerValidationResponse();

        if (customerRegistrationDetailsDto.getEmail().isEmpty() || customerRegistrationDetailsDto.getEmail().equals("") || customerRegistrationDetailsDto.getEmail() == null) {
            customerValidationResponse.setStatus("false");
            customerValidationResponse.setMessage("Mobile Number can't be empty!");
            return customerValidationResponse;
        }
        if (customerRegistrationDetailsDto.getFirst_name().isEmpty() || customerRegistrationDetailsDto.getFirst_name().equals("") || customerRegistrationDetailsDto.getFirst_name() == null) {
            customerValidationResponse.setStatus("false");
            customerValidationResponse.setMessage("First Name can't be empty!");
        }
            customerValidationResponse.setStatus("true");
        return customerValidationResponse;
    }
}
