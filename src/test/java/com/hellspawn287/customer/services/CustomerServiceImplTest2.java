package com.hellspawn287.customer.services;

import com.hellspawn287.customer.domain.CustomerEntity;
import com.hellspawn287.customer.repositories.CustomerRepository;
import com.hellspawn287.customer.web.controller.CustomerController;
import com.hellspawn287.customer.web.mapper.CustomerMapper;
import com.hellspawn287.customer.web.model.CustomerDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest2 {
    private static final UUID CUSTOMER_ID = UUID.randomUUID();
    private static final String f_NAME = "Peter";
    private static final String l_NAME = "Parker";

    @Mock
    CustomerRepository customerRepository;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private CustomerServiceImpl customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl();
        customerService.setCustomerMapper(customerMapper);
        customerService.setCustomerRepository(customerRepository);
    }

    @Test
    public void getAllCustomers() {
        //given
        List<CustomerEntity> customers = Arrays.asList(new CustomerEntity(), new CustomerEntity(), new CustomerEntity());

        when(customerRepository.findAll()).thenReturn(customers);
        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    public void getCustomerById() {
        //given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(CUSTOMER_ID);
        customer.setFirstname(f_NAME);
        customer.setLastname(l_NAME);

        when(customerRepository.findById(any(UUID.class))).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(CUSTOMER_ID);

        //then
        assertEquals(f_NAME, customerDTO.getFirstname());
        assertEquals(l_NAME, customerDTO.getLastname());
    }

    @Test
    public void CreateNewCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        CustomerEntity savedCustomer = new CustomerEntity();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(CUSTOMER_ID);

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals(CustomerController.BASE_URL + "/" + CUSTOMER_ID, savedDTO.getCustomerURL());
    }

    @Test
    public void saveCustomerByDTO() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(f_NAME);

        CustomerEntity savedCustomer = new CustomerEntity();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(CUSTOMER_ID);

        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedCustomer);

        // when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(CUSTOMER_ID, customerDTO);

        // then
        assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
        assertEquals(CustomerController.BASE_URL + "/" + CUSTOMER_ID, savedDTO.getCustomerURL());
    }

    @Test
    public void deleteCustomerById() throws Exception {
        UUID id = UUID.randomUUID();

        customerService.deleteCustomerById(id);
        verify(customerRepository, Mockito.times(1)).deleteById(any(UUID.class));
    }
}