package com.hellspawn287.customer.services;

import com.hellspawn287.customer.domain.CustomerEntity;
import com.hellspawn287.customer.repositories.CustomerRepository;
import com.hellspawn287.customer.web.controller.CustomerController;
import com.hellspawn287.customer.web.mapper.CustomerMapper;
import com.hellspawn287.customer.web.model.CustomerDTO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerMapper customerMapper;
    private CustomerRepository customerRepository;

    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    customerDTO.setCustomerURL(getCustomerUrl(customer.getId()));
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(UUID customerID) {
        return customerRepository.findById(customerID)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDTOToCustomer(customerDTO));
    }

    private CustomerDTO saveAndReturnDTO(CustomerEntity customerEntity) {
        CustomerEntity savedCustomerEntity = customerRepository.save(customerEntity);
        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomerEntity);
        returnDTO.setCustomerURL(getCustomerUrl(savedCustomerEntity.getId()));

        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(UUID id, CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerMapper.customerDTOToCustomer(customerDTO);
        customerEntity.setId(id);

        return saveAndReturnDTO(customerEntity);
    }

    @Override
    public CustomerDTO patchCustomer(UUID id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customerEntity -> {

            if (customerDTO.getFirstname() != null) {
                customerEntity.setFirstname(customerDTO.getFirstname());
            }
            if (customerDTO.getLastname() != null) {
                customerEntity.setLastname(customerDTO.getLastname());
            }
            CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customerEntity));
            returnDTO.setCustomerURL(getCustomerUrl(id));

            return returnDTO;

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(UUID id) {
        customerRepository.deleteById(id);
    }

    private String getCustomerUrl(UUID id) {
        return CustomerController.BASE_URL + "/" + id;
    }
}