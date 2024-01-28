package com.loginService.service.servicesImpl;

import com.loginService.common.Constants;
import com.loginService.dto.CustomerDataDto;
import com.loginService.dto.Data;
import com.loginService.dto.ResponseModel;
import com.loginService.entities.CustomerRegistration;
import com.loginService.repositories.CustomerRegistrationRepo;
import com.loginService.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRegistrationRepo customerRegistrationRepo;
    @Override
    public ResponseModel getAllCustomer() {
        ResponseModel responseModel = new ResponseModel();
        List<CustomerRegistration> customerRegistration = customerRegistrationRepo.findAll();
        List<CustomerDataDto> customerList = new ArrayList<>();

        for(CustomerRegistration obj:customerRegistration){
            CustomerDataDto customer = new CustomerDataDto();
            BeanUtils.copyProperties(obj,customer);
            customerList.add(customer);
        }
        Data data = new Data();
        responseModel.setStatus(Constants.successStatus);
        responseModel.setStatusCode(Constants.successCode);
        data.setCustomerDetailsList(customerList);
        responseModel.setData(data);
        return responseModel;
    }

    @Override
    public ResponseModel getCustomerById(String id) {
        Long customerId = Long.valueOf(id);
        ResponseModel responseModel = new ResponseModel();
        CustomerRegistration customerRegistration = customerRegistrationRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer with ID " + customerId + " not found"));

            CustomerDataDto customer = new CustomerDataDto();
            BeanUtils.copyProperties(customerRegistration,customer);

        Data data = new Data();
        data.setCustomerDataDto(customer);
        responseModel.setStatus(Constants.successStatus);
        responseModel.setStatusCode(Constants.successCode);
        responseModel.setData(data);
        return responseModel;
    }

}
