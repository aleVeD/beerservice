package guru.springframework.beerdemo.service;

import guru.springframework.beerdemo.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class CustomerServiceImpl implements CustomerService {
  @Override
  public CustomerDto getCustomerById(UUID id) {
    return CustomerDto.builder().id(UUID.randomUUID())
            .customerName("juanito perez")
            .build();
  }

  @Override
  public CustomerDto saveNewCustomer(CustomerDto customerDto) {
    return CustomerDto.builder().id(UUID.randomUUID()).build();
  }

  @Override
  public void updateCustomer(UUID customerId, CustomerDto customerDto) {

  }

}
