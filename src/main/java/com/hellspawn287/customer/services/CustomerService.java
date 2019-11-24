package com.hellspawn287.customer.services;

import com.hellspawn287.customer.web.model.CustomerDTO;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerDTO getCustomerById(UUID id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO createNewCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomerByDTO(UUID id, CustomerDTO customerDTO);

    CustomerDTO patchCustomer(UUID id, CustomerDTO customerDTO);

    void deleteCustomerById(UUID id);
}