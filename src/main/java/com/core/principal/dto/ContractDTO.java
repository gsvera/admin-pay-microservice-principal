package com.core.principal.dto;

import com.core.principal.entity.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data @AllArgsConstructor @NoArgsConstructor
public class ContractDTO {
    private Long id;
    private int idCustomer;
    private String idCreatedUser;
    private String folio;
    private String startDate;
    private String endDate;
    private String typeAmortization;
    private String period;
    private int numberPeriod;
    private double amount;
    private String interestRate;
    private double interest;
    private int statusContract;
    ArrayList<ContractPayDTO> listPay;
    //  PARAMETROS EXTRAS
    private String nameUserCreated;
    private String customerFirstName;
    private String customerLastName;
    private String lastDateDidPay;
    private double lastPayAmount;

    public ContractDTO (Contract contract) {
        this.id = contract.getId();
        this.idCustomer = contract.getIdCustomer();
        this.idCreatedUser = contract.getIdCreatedUser();
        this.folio = contract.getFolio();
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
        this.typeAmortization = contract.getTypeAmortization();
        this.period = contract.getPeriod();
        this.numberPeriod = contract.getNumberPeriod();
        this.amount = contract.getAmount();
        this.interestRate = contract.getInterestRate();
        this.interest = contract.getInterest();
        this.statusContract = contract.getStatusContract();
    }
}
