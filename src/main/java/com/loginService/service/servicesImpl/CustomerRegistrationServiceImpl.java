package com.loginService.service.servicesImpl;

import com.loginService.common.CommonValidater;
import com.loginService.common.Constants;
import com.loginService.dto.*;
import com.loginService.entities.CustomerRegistration;
import com.loginService.repositories.CustomerRegistrationRepo;
import com.loginService.service.CustomerRegistrationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class CustomerRegistrationServiceImpl implements CustomerRegistrationService {
    @Autowired
    private CustomerRegistrationRepo customerRegistrationRepo;

    // creating or saving data of a new customer here
    @Override
    public ResponseModel registerCustomer(CustomerRegistrationDetailsDto customerRegistrationDetailsDto) {
        ResponseModel responseModel = new ResponseModel();
        CustomerRegistration customerRegistration = new CustomerRegistration();
        CustomerValidationResponse customerValidationResponse = CommonValidater.validateCustomerRegistration(customerRegistrationDetailsDto);
        if (customerValidationResponse.getStatus().equalsIgnoreCase("false")) {
            responseModel.setMessage(customerValidationResponse.getMessage());
            return responseModel;
        }
        // Generate a random long value
        long randomId = System.currentTimeMillis();


        BeanUtils.copyProperties(customerRegistrationDetailsDto, customerRegistration);
            customerRegistration.setUuid(randomId+"");
            CustomerRegistration returnedCustomerRegistration = customerRegistrationRepo.save(customerRegistration);
            if (!(returnedCustomerRegistration == null)) {
                responseModel.setStatusCode(Constants.successCode);
                responseModel.setStatus(Constants.successStatus);
                responseModel.setMessage("Customer Created Successfully");
                Data data = new Data();
                CustomerRegistrationDetailsDto registeredCustomer = new CustomerRegistrationDetailsDto();
                BeanUtils.copyProperties(returnedCustomerRegistration, registeredCustomer);
                data.setCustomerRegistrationDetailsDto(registeredCustomer);
                responseModel.setData(data);
            } else {
                responseModel.setMessage("Customer Creation Failed");
            }
        return responseModel;
    }

    // delete the customer using id
    // after deleting we will return the list of left customers
    @Override
    @Transactional
    public ResponseModel deleteCustomer(String id) {
        ResponseModel responseModel = new ResponseModel();
        List<CustomerDataDto> returnCustList = new ArrayList<>();
        Data data = new Data();
        Long customerId = Long.valueOf(id);
        customerRegistrationRepo.deleteById(customerId);
        List<CustomerRegistration> list = customerRegistrationRepo.findAll();
        for(CustomerRegistration customer:list){
            CustomerDataDto customerDataDto = new CustomerDataDto();
            BeanUtils.copyProperties(customer,customerDataDto);
            returnCustList.add(customerDataDto);
        }
        data.setCustomerDetailsList(returnCustList);
        responseModel.setData(data);
        responseModel.setStatusCode(Constants.successCode);
        responseModel.setStatus(Constants.successStatus);
        responseModel.setMessage("Customer deleted successfully!");
        return responseModel;
    }
// remote api calling, it will return a list of customer data which we have to save in our database
    @Override
    public ResponseModel syncCustomer(String token) {
      ResponseModel responseModel = new ResponseModel();
      Data data = new Data();
        List<CustomerDataDto> returnCustList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        System.out.println("token : "+token);
        String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<List<CustomerDataDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
                    requestEntity, new ParameterizedTypeReference<List<CustomerDataDto>>() { });

            System.out.println("responseEntity : "+responseEntity);
            List<CustomerDataDto> customerDataList = responseEntity.getBody();
            System.out.println(customerDataList);

     // save the customers received from remote api
            for(CustomerDataDto customer:customerDataList){

                Optional<CustomerRegistration> existingCustomerRegistration = customerRegistrationRepo.findByUuid(customer.getUuid());
                if (existingCustomerRegistration.isPresent()) {
                    continue;
                }
                CustomerRegistration customerRegistration = new CustomerRegistration();
                BeanUtils.copyProperties(customer,customerRegistration);
                customerRegistrationRepo.save(customerRegistration);
            }
            responseModel.setMessage("Syncing completed successfully!");
        }catch (Exception e){
            e.printStackTrace();
            responseModel.setMessage("Exception from remote api");
        }
   // get all customer from database to return
        returnCustList.clear();  // clear the previous list to use again
        List<CustomerRegistration> list = customerRegistrationRepo.findAll();
        System.out.println(list);
        for(CustomerRegistration customer:list){
            CustomerDataDto customerDataDto = new CustomerDataDto();
            BeanUtils.copyProperties(customer,customerDataDto);
            returnCustList.add(customerDataDto);
        }
        data.setCustomerDetailsList(returnCustList);
        responseModel.setData(data);
        return responseModel;
    }
    @Override
    public ResponseModel updateCustomer(CustomerDataDto customerDataDto) {
        ResponseModel responseModel = new ResponseModel();
        Optional<CustomerRegistration> existingCustomerRegistration = customerRegistrationRepo.findById(customerDataDto.getId());
        if (existingCustomerRegistration.isPresent()) {
            CustomerRegistration existingEntity = existingCustomerRegistration.get();
            BeanUtils.copyProperties(customerDataDto,existingEntity);
            customerRegistrationRepo.updateCustomerRegistration(existingEntity);
            responseModel.setStatus(Constants.successStatus);
            responseModel.setStatusCode(Constants.successCode);
            responseModel.setMessage("Customer Updated Successfully!");
        } else {
            responseModel.setMessage("No such customer!");
        }
        return responseModel;
    }

}
