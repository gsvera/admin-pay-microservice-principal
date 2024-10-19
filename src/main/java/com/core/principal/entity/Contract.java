package com.core.principal.entity;

import com.core.principal.dto.ContractDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_contract")
public class Contract {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_customer")
    private Long idCustomer;
    @Column(name = "id_created_user")
    private String idCreatedUser;
    private String folio;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;
    @Column(name = "type_amortization")
    private String typeAmortization;
    private String period;
    @Column(name = "number_period")
    private int numberPeriod;
    private double amount;
    @Column(name = "interest_rate")
    private String interestRate;
    private double interest;
    @Column(name = "status_contract")
    private int statusContract;

    public Contract(ContractDTO contractDTO) {
        this.id = contractDTO.getId();
        this.idCustomer = contractDTO.getIdCustomer();
        this.idCreatedUser = contractDTO.getIdCreatedUser();
        this.folio = contractDTO.getFolio();
        this.startDate = contractDTO.getStartDate();
        this.endDate = contractDTO.getEndDate();
        this.typeAmortization = contractDTO.getTypeAmortization();
        this.period = contractDTO.getPeriod();
        this.numberPeriod = contractDTO.getNumberPeriod();
        this.amount = contractDTO.getAmount();
        this.interestRate = contractDTO.getInterestRate();
        this.interest = contractDTO.getInterest();
        this.statusContract = contractDTO.getStatusContract();
    }
}
