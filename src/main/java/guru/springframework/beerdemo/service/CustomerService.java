package guru.springframework.beerdemo.service;

import guru.springframework.beerdemo.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {
  CustomerDto getCustomerById(UUID id);

  CustomerDto saveNewCustomer(CustomerDto customerDto);


  void updateCustomer(UUID customerId, CustomerDto customerDto);

  void deleteCustomerById(UUID customerId);
}
