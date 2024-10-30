package com.core.principal.entity;

import com.core.principal.dto.ContractPayDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_pay_contract")
public class ContractPay {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pay_number")
    private int payNumber;
    @ManyToOne
    @JoinColumn(name = "id_contract", insertable = true, updatable = true, nullable = false)
    @JsonBackReference
    private Contract idContractRelation;
    @Column(name = "id_created_user")
    private String idCreatedUser;
    @Column(name = "created_name_user")
    private String createdNameUser;
    @Column(name = "agreed_date")
    private String agreedDate;
    @Column(name = "agreed_pay")
    private double agreedPay;
    @Column(name = "pay_date")
    private String payDate;
    @Column(name = "pay_amount")
    private double payAmount;
    @Column(name = "pay_file")
    private String payFile;
    @Column(name = "pay_did_date")
    private String payDidDate;
    @Column(name = "status_pay")
    private int statusPay;
    public ContractPay(ContractPayDTO contractPayDTO) {
        this.id = contractPayDTO.getId();
        this.idContractRelation = contractPayDTO.getIdContract();
        this.payNumber = contractPayDTO.getPayNumber();
        this.idCreatedUser = contractPayDTO.getIdCreatedUser();
        this.createdNameUser = contractPayDTO.getCreatedNameUser();
        this.agreedDate = contractPayDTO.getAgreedDate();
        this.agreedPay = contractPayDTO.getAgreedPay();
        this.payDate = contractPayDTO.getPayDate();
        this.payAmount = contractPayDTO.getPayAmount();
        this.payFile = contractPayDTO.getPayFile();
        this.payDidDate = contractPayDTO.getPayDidDate();
    }
}
