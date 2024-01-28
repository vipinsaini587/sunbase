package com.loginService.dto;

import lombok.Data;

@Data
public class CustomerDataDto {
    private Long id;
    private String first_name;
    private String last_name;
    private String street;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;
}
