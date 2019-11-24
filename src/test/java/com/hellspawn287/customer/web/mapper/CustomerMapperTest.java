package com.hellspawn287.customer.web.mapper;

import com.hellspawn287.customer.domain.CustomerEntity;
import com.hellspawn287.customer.web.model.CustomerDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {
    private static final String f_NAME = "Peter";
    private static final String l_NAME = "Parker";
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void customerToCustomerDTO() {
//      given
        CustomerEntity customer = new CustomerEntity();
        customer.setFirstname(f_NAME);
        customer.setLastname(l_NAME);
//      when
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

//      then
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }

    @Test
    public void customerDTOToCustomer() {
//        given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(f_NAME);
        customerDTO.setLastname(l_NAME);

//        when
        CustomerEntity customer = customerMapper.customerDTOToCustomer(customerDTO);

//        then
        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
    }

}