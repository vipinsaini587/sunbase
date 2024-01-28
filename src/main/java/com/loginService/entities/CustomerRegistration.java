package com.loginService.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="sunbase_user_details")

public class CustomerRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
