package com.core.principal.dto;

import com.core.principal.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {
    public Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String celPhone;
    private boolean celPhoneWhatsapp;
    private String phoneOffice;
    private boolean phoneOfficeWhatsapp;
    private String description;
    private String createdUser;
    private String updatedUser;
    private String codeCompany;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public CustomerDTO(Customer customer){
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.celPhone = customer.getCelPhone();
        this.phoneOffice = customer.getPhoneOffice();
        this.description = customer.getDescription();
        this.celPhoneWhatsapp = customer.isCelPhoneWhatsapp();
        this.phoneOfficeWhatsapp = customer.isPhoneOfficeWhatsapp();
    }
}
