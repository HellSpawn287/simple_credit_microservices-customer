package com.hellspawn287.customer.web.mapper;

import com.hellspawn287.customer.domain.CustomerEntity;
import com.hellspawn287.customer.web.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    //    @Mapping(target = "customerURL", ignore = true)
    CustomerDTO customerToCustomerDTO(CustomerEntity customerEntity);

    CustomerEntity customerDTOToCustomer(CustomerDTO customerDTO);

}