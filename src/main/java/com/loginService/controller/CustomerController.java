package com.loginService.controller;

import com.loginService.dto.CustomerDataDto;
import com.loginService.dto.CustomerLoginDetailsDto;
import com.loginService.dto.ResponseModel;
import com.loginService.security.JwtUtils;
import com.loginService.service.CustomerRegistrationService;
import com.loginService.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerService findcustomerService;
    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    // delete controller
    //token will be returned for further usage
    @GetMapping("/delete-customer/{id}/{token}")
    public ModelAndView deleteCustomer(@PathVariable String id, @PathVariable String token){
        System.out.println(token);
        ModelAndView modelAndView = new ModelAndView();
        ResponseModel responseModel = customerRegistrationService.deleteCustomer(id);
        modelAndView.addObject("message",responseModel.getMessage());
        modelAndView.addObject("token",token);
        modelAndView.addObject("customerList",responseModel.getData().getCustomerDetailsList());
        modelAndView.setViewName("Dashboard");
        return modelAndView;
    }

    // sync controller
    // will call remote api which will return a list of customers data will save that data
    // into our database
    @PostMapping("/sync-customer/{token}")
    public ModelAndView syncCustomer(@PathVariable String token, @ModelAttribute CustomerLoginDetailsDto customerLoginDetailsDto){
        ModelAndView modelAndView = new ModelAndView();
        ResponseModel responseModel = customerRegistrationService.syncCustomer(token);
        modelAndView.addObject("token",token);
        modelAndView.addObject("message",responseModel.getMessage());
        modelAndView.addObject("customerList",responseModel.getData().getCustomerDetailsList());
        modelAndView.setViewName("Dashboard");
        return modelAndView;
    }
    @PutMapping("/update-customer")
    public ResponseEntity<ResponseModel> updateCustomer(@RequestBody CustomerDataDto customerDataDto){
        ResponseModel responseModel = new ResponseModel();
        responseModel = customerRegistrationService.updateCustomer(customerDataDto);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/get-all-customer")
    public ResponseEntity<ResponseModel> getAllCustomer(@RequestHeader ("Authorization")String token){
        ResponseModel responseModel = findcustomerService.getAllCustomer();
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }
    @GetMapping("/get-customer/{id}")
    public ResponseEntity<ResponseModel> getCustomer(@RequestHeader ("Authorization")String token,@PathVariable String id){
        ResponseModel responseModel = new ResponseModel();

        if(token.equals("")||token==null||!token.startsWith("Bearer")){
            responseModel.setMessage("No token found!");
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        if(jwtUtils.tokenExpired(token)){
            responseModel.setMessage("Session Expired!");
            return new ResponseEntity<>(responseModel, HttpStatus.OK);
        }

        responseModel = findcustomerService.getCustomerById(id);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

}
