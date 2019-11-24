package com.hellspawn287.customer.web.controller;

import com.hellspawn287.customer.services.CustomerService;
import com.hellspawn287.customer.services.ResourceNotFoundException;
import com.hellspawn287.customer.web.model.CustomerDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends AbstractRestControllerTest {
    private static final String f_NAME = "Peter";
    private static final String l_NAME = "Parker";
    private static final UUID CUSTOMER_ID1 = UUID.fromString("90c8a28c-34b4-497e-80c7-a89adacdf148");
    private static final UUID CUSTOMER_ID2 = UUID.fromString("e4253144-ca31-4ea8-a1c5-8be07f6bfbf5");

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

//    @Test
//    public void getAllCustomers() throws Exception {
//        //given
//        CustomerDTO customer1 = new CustomerDTO();
//        customer1.setFirstname(f_NAME);
//        customer1.setLastname(l_NAME);
//        customer1.setCustomerURL("/api/customers/" + CUSTOMER_ID + 2);
//
//        CustomerDTO customer2 = new CustomerDTO();
//        customer2.setFirstname(f_NAME);
//        customer2.setLastname(l_NAME);
//        customer2.setCustomerURL(CustomerController.BASE_URL + "/" + CUSTOMER_ID);
//
//        //when
//        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));
//
//        //then
//        mockMvc.perform(
//                get("/api/customers/")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.customers", hasSize(2)));
//    }

    @Test
    public void getCustomerByID() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstname(f_NAME);
        customer1.setLastname(l_NAME);
        customer1.setCustomerURL("/api/customers/" + CUSTOMER_ID1);

        //when
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(customer1);

        mockMvc.perform(
                get(CustomerController.BASE_URL + "/" + CUSTOMER_ID1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(f_NAME)))
                .andExpect(jsonPath("$.lastname", equalTo(l_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/customers/" + "90c8a28c-34b4-497e-80c7-a89adacdf148")));
    }

    @Test
    public void createNewCustomer() throws Exception {
        // given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Fred");
        customerDTO.setLastname("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstname(customerDTO.getFirstname());
        returnDTO.setLastname(customerDTO.getLastname());
        returnDTO.setCustomerURL("/api/customers/" + "e4253144-ca31-4ea8-a1c5-8be07f6bfbf5");

        when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);

        // then
        mockMvc.perform(post(CustomerController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname", equalTo("Fred")))
                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/" + CUSTOMER_ID2)));
    }

//    @Test
//    public void testUpdateCustomer() throws Exception {
//        // given
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setFirstname(f_NAME);
//        customerDTO.setLastname(l_NAME);
//
//        CustomerDTO returnDTO = new CustomerDTO();
//        returnDTO.setFirstname(customerDTO.getFirstname());
//        returnDTO.setLastname(customerDTO.getLastname());
//        returnDTO.setCustomerURL(CustomerController.BASE_URL + "/" + CUSTOMER_ID2);
//
//        when(customerService.saveCustomerByDTO(any(UUID.class), any(CustomerDTO.class))).thenReturn(returnDTO);
//        // when/then
//
//        mockMvc.perform(
//                put(CustomerController.BASE_URL + "/" + CUSTOMER_ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(customerDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstname", equalTo(f_NAME)))
//                .andExpect(jsonPath("$.lastname", equalTo(l_NAME)))
//                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/" + "e4253144-ca31-4ea8-a1c5-8be07f6bfbf5")));
//    }

//    @Test
//    public void testPatchCustomer() throws Exception {
//        CustomerDTO customerDTO = new CustomerDTO();
//        customerDTO.setFirstname(f_NAME);
//
//        CustomerDTO returnDTO = new CustomerDTO();
//        returnDTO.setFirstname(customerDTO.getFirstname());
//        returnDTO.setLastname(l_NAME);
//        returnDTO.setCustomerURL(CustomerController.BASE_URL + "/" + CUSTOMER_ID2);
//
//        when(customerService.patchCustomer(any(UUID.class), any(CustomerDTO.class))).thenReturn(returnDTO);
//
//        mockMvc.perform(patch(CustomerController.BASE_URL + "/" + CUSTOMER_ID2)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(customerDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.firstname", equalTo(f_NAME)))
//                .andExpect(jsonPath("$.lastname", equalTo(l_NAME)))
//                .andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/" + "e4253144-ca31-4ea8-a1c5-8be07f6bfbf5")));
//    }

//    @Test
//    public void testDeleteCustomerById() throws Exception {
//        mockMvc.perform(delete(CustomerController.BASE_URL + "/" + CUSTOMER_ID2)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        verify(customerService).deleteCustomerById(any(UUID.class));
//    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(customerService.getCustomerById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CustomerController.BASE_URL + "/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}