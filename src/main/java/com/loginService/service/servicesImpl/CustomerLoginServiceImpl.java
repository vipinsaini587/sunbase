package com.loginService.service.servicesImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loginService.dto.CustomerLoginDetailsDto;
import com.loginService.security.JwtUtils;
import com.loginService.service.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService {
    @Autowired
    private JwtUtils jwtUtil;

// calling remote api here, will get a access token in return which will be used to get customers list
    @Override
    public String apiCall(CustomerLoginDetailsDto customerLoginDetailsDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String url = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
        HttpEntity<CustomerLoginDetailsDto> requestEntity = new HttpEntity<>(customerLoginDetailsDto, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity,String.class);
        String responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        String accessToken = null;
        try {
            jsonNode = objectMapper.readTree(responseBody);
            accessToken = jsonNode.get("access_token").asText();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(accessToken);
        return  accessToken;
    }

}
