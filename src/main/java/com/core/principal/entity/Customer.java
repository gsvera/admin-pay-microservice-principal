package com.core.principal.entity;

import com.core.principal.dto.CustomerDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "cel_phone")
    private String celPhone;
    @Column(name = "cel_phone_whatsapp")
    private boolean celPhoneWhatsapp;
    @Column(name = "phone_office")
    private String phoneOffice;
    @Column(name = "phone_office_whatsapp")
    private boolean phoneOfficeWhatsapp;
    private String description;
    @Column(name = "created_user")
    private String createdUser;
    @Column(name = "updated_user")
    private String updatedUser;
    @Column(name = "code_company")
    private String codeCompany;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
    @Column(name = "delete_register")
    private boolean deleteRegister;
    public Customer (CustomerDTO customerDTO) {
        this.firstName = customerDTO.getFirstName();
        this.lastName = customerDTO.getLastName();
        this.email = customerDTO.getEmail();
        this.celPhone = customerDTO.getCelPhone();
        this.phoneOffice = customerDTO.getPhoneOffice();
        this.description = customerDTO.getDescription();
        this.codeCompany = customerDTO.getCodeCompany();
        this.createdUser = customerDTO.getCreatedUser();
        this.celPhoneWhatsapp = customerDTO.isCelPhoneWhatsapp();
        this.phoneOfficeWhatsapp = customerDTO.isCelPhoneWhatsapp();
    }
}
