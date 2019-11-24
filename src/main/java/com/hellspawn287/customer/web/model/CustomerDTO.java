package com.hellspawn287.customer.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {

    private UUID id;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @PESEL
    private String peselNumber;

//    @Past
//    private Timestamp dateOfBirth;

    @JsonProperty("customer_url")
    private String customerURL;
}