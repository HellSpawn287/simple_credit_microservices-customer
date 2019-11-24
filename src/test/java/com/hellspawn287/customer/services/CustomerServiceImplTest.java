package com.hellspawn287.customer.services;


import com.hellspawn287.customer.domain.CustomerEntity;
import com.hellspawn287.customer.repositories.CustomerRepository;
import com.hellspawn287.customer.web.model.CustomerDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class CustomerServiceImplTest {

    @Autowired
    CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() throws ResourceNotFoundException {
        System.out.println("Loading Customer Data...");
        System.out.println(customerRepository.findAll().size());

        //load data...
        CustomerEntity jim = new CustomerEntity();
        jim.setFirstname("John");
        jim.setLastname("Jameson");

        CustomerEntity jack = new CustomerEntity();
        jack.setFirstname("Jack");
        jack.setLastname("Nicholson");

        CustomerEntity peter = new CustomerEntity();
        peter.setFirstname("Peter");
        peter.setLastname("Parker");

        customerRepository.save(jim);
        customerRepository.save(jack);
        customerRepository.save(peter);

        customerService = new CustomerServiceImpl();
    }

    @Test
    public void patchCustomerUpdateFirstName() throws ResourceNotFoundException {
        String updatedName = "UpdatedName";
        UUID id = getCustomerIdValue();

        CustomerEntity originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        CustomerEntity updatedCustomer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstname());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstname())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastname()));
    }

    @Test
    public void patchCustomerUpdateLastName() throws ResourceNotFoundException {
        String updatedName = "UpdatedName";
        UUID id = getCustomerIdValue();

        CustomerEntity originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);

        //save original first name/last name
        String originalFirstName = originalCustomer.getFirstname();
        String originalLastName = originalCustomer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(id, customerDTO);

        CustomerEntity updatedCustomer = customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastname());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstname()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastname())));
    }

    private UUID getCustomerIdValue() {
        List<CustomerEntity> customers = customerRepository.findAll();
        System.out.println("Customers found: " + customers.size());

        //return first id
        return customers.get(0).getId();
    }
}