package guru.springframework.beerdemo.mappers;

import guru.springframework.beerdemo.domain.Customer;
import guru.springframework.beerdemo.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
  Customer customerDtoToCustomer(CustomerDto customerDto);
  CustomerDto customerDtoToCustomer(Customer customer);
}
