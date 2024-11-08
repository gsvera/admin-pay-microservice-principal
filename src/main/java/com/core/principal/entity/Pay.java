package com.core.principal.entity;

import com.core.principal.dto.ContractPayDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_pay")
public class Pay {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_contract", insertable = true, updatable = true, nullable = false)
    @JsonBackReference
    private Contract idContractXPay;
    @Column(name = "pay_amount")
    private double payAmount;
    @Column(name = "pay_file")
    private String payFile;
    @Column(name = "pay_date")
    private String payDate;
    @Column(name = "id_created_user")
    private String idCreatedUser;
    @Column(name = "ids_pay_contract")
    private String idsPayContract;
    public Pay(ContractPayDTO contractPayDTO) {
        this.idContractXPay = contractPayDTO.getIdContract();
        this.payAmount = contractPayDTO.getPayAmount();
        this.payFile = contractPayDTO.getPayFile();
        this.payDate = contractPayDTO.getPayDidDate();
        this.idCreatedUser = contractPayDTO.getIdCreatedUser();
    }
}
