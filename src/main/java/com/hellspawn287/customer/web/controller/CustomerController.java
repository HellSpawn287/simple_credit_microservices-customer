package com.hellspawn287.customer.web.controller;

import com.hellspawn287.customer.services.CustomerService;
import com.hellspawn287.customer.web.model.CustomerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {
    public static final String BASE_URL = "/api/customers";
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public CustomerListDTO getAllCustomers() {
//        return new CustomerListDTO(
//                customerService.getAllCustomers());
//    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerByID(@PathVariable UUID id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

//    @PutMapping({"/{id}"})
//    @ResponseStatus(HttpStatus.OK)
//    public CustomerDTO updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO) {
//        return customerService.saveCustomerByDTO(id, customerDTO);
//    }

//    @PatchMapping({"/{id}"})
//    @ResponseStatus(HttpStatus.OK)
//    public CustomerDTO patchCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO) {
//        return customerService.patchCustomer(id, customerDTO);
//    }

//    @DeleteMapping({"/{id}"})
//    @ResponseStatus(HttpStatus.OK)
//    public void deleteCustomerById(@PathVariable UUID id) {
//        customerService.deleteCustomerById(id);
//    }
}