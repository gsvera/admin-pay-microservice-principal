package com.core.principal.dto;

import com.core.principal.entity.Contract;
import lombok.Data;

@Data
public class ContractPayDTO {
    private Long id;
    private int payNumber;
    private Contract idContract;
    private int idContractDto;
    private String idCreatedUser;
    private String createdNameUser;
    private String agreedDate;
    private double agreedPay;
    private String payDate;
    private double payAmount;
    private String payFile;
    private String payDidDate;
    private int statusPay;
}
