package com.loginService.controller;

import com.loginService.dto.CustomerRegistrationDetailsDto;
import com.loginService.dto.ResponseModel;
import com.loginService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {
    @Autowired
    private CustomerService findcustomerService;

    @GetMapping("/")
    public ModelAndView loginPage(){
         return new ModelAndView("Login");
    }
    @GetMapping("/add-customer")
    public ModelAndView addCustomerPage(){
        return new ModelAndView("AddCustomer");
    }
    @GetMapping("/dashboard/{token}")
    public ModelAndView customerDashboard(@PathVariable String token){
        ModelAndView modelAndView = new ModelAndView();
        ResponseModel responseModel = findcustomerService.getAllCustomer();
        modelAndView.setViewName("Dashboard");
        modelAndView.addObject("token",token);
        modelAndView.addObject("customerList",responseModel.getData().getCustomerDetailsList());

        return modelAndView;
    }
}
