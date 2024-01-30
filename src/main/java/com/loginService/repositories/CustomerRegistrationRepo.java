package com.loginService.repositories;

import com.loginService.entities.CustomerRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRegistrationRepo extends JpaRepository<CustomerRegistration, Long> {
     Optional<CustomerRegistration> findById(Long id);
     Optional<CustomerRegistration> findByUuid(String id);
     default CustomerRegistration updateCustomerRegistration(CustomerRegistration customerRegistration) {
          return save(customerRegistration);
     }
}
