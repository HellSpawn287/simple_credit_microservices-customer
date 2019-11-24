package com.hellspawn287.customer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotBlank(message = "Please enter your first name")
    private String firstname;
    @NotBlank(message = "Please enter your last name")
    private String lastname;

    @PESEL(message = "Please enter your PESEL number")
    @Transient
    private String peselNumber;

//    @Past
//    private Date dateOfBirth;

    public CustomerEntity(@NotBlank String firstname, @NotBlank String lastname, @PESEL String peselNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.peselNumber = peselNumber;
    }
}