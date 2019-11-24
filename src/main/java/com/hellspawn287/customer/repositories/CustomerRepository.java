package com.hellspawn287.customer.repositories;

import com.hellspawn287.customer.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

    @Override
    CustomerEntity getOne(UUID uuid);
}