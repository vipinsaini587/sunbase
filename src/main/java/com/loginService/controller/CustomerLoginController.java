package com.loginService.controller;

import com.loginService.dto.CustomerRegistrationDetailsDto;
import com.loginService.dto.ResponseModel;
import com.loginService.dto.CustomerLoginDetailsDto;
import com.loginService.service.CustomerLoginService;
import com.loginService.service.CustomerRegistrationService;
import com.loginService.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/customer-login")
public class CustomerLoginController {
    @Autowired
    private CustomerLoginService customerLoginService;
    @Autowired
    private CustomerService findcustomerService;
    @Autowired
    private CustomerRegistrationService customerRegistrationService;


    // login controller
    // using remote api to generate a token
    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute CustomerLoginDetailsDto customerLoginDetailsDto){
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(customerLoginDetailsDto);
        String token = customerLoginService.apiCall(customerLoginDetailsDto);
            ResponseModel customers = findcustomerService.getAllCustomer();
            modelAndView.addObject("token",token);
            modelAndView.addObject("customerList",customers.getData().getCustomerDetailsList());
            modelAndView.setViewName("Dashboard");
        return  modelAndView;
}

// this controller is used to save customer data from add customer page
    @PostMapping("/register-customer")
    public ModelAndView registerCustomer(@ModelAttribute CustomerRegistrationDetailsDto customerRegistrationDetailsDto){
        ModelAndView modelAndView = new ModelAndView();
        ResponseModel responseModel = customerRegistrationService.registerCustomer(customerRegistrationDetailsDto);
        System.out.println(responseModel.getMessage());
        modelAndView.addObject("message",responseModel.getMessage());
        modelAndView.setViewName("AddCustomer");
        System.out.println("ModelAndView : "+modelAndView);
        return modelAndView;
    }

}
