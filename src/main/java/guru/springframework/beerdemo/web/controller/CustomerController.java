package guru.springframework.beerdemo.web.controller;

import guru.springframework.beerdemo.service.CustomerService;
import guru.springframework.beerdemo.web.model.CustomerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/customer")
@RestController
public class CustomerController {

  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/{customerId}")
  public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID id){
    return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CustomerDto> handlePost(@RequestBody CustomerDto customerDto){
    CustomerDto cus = customerService.saveNewCustomer(customerDto);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("location", "/api/v1/customer/"+ cus.getId().toString());
    return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
  }

  @PutMapping("/{customerId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void handleUpdate(@RequestBody CustomerDto customerDto, @PathVariable("customerId") UUID customerId){
    customerService.updateCustomer(customerId, customerDto);
  }

  @DeleteMapping("/{customerId}")
  public void handleDelete(@PathVariable("customerId") UUID customerId){

  }
}
