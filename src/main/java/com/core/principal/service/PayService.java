package com.core.principal.service;

import com.core.principal.config.AppConfig;
import com.core.principal.dto.ContractPayDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.entity.Contract;
import com.core.principal.entity.ContractPay;
import com.core.principal.repository.ContractRepository;
import com.core.principal.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service @RequiredArgsConstructor
public class PayService {
    private final PayRepository payRepository;
    private final ContractRepository contractRepository;
    private final AppConfig appConfig;
    public ResponseDTO _SavePay(ContractPayDTO contractPayDTO, boolean force) {
        Contract contract = contractRepository.findByIdContract(Long.valueOf(contractPayDTO.getIdContractDto()));
        if(contract != null) {
            double amountToPay = contractPayDTO.getPayAmount();
            ArrayList<ContractPay> listPays = payRepository.findByIdContractPaysPending(contractPayDTO.getIdContractDto());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formatterDb = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate dateDidPay = LocalDate.parse(contractPayDTO.getPayDidDate(), formatter);

            ArrayList<ContractPay> listPayComplete = new ArrayList<>();
            Map<String, Object> obj = new HashMap<>();

            for(ContractPay pay : listPays) {
                LocalDate payDateCompare = LocalDate.parse(pay.getAgreedDate(), formatterDb);

                // Si la fecha de pago es menor o igual a la fecha pactada entra a validacion de pagos
                if(dateDidPay.isBefore(payDateCompare) || dateDidPay.isEqual(payDateCompare)) {
                    // Si el monto es mayor al pago acordado y no esta forzado devuelve advertencia
                    if(amountToPay > (pay.getAgreedPay() - pay.getPayAmount()) && force == false ) {
                        obj.put("typesPay", appConfig.getPayMoreToAmount());
                        return ResponseDTO.builder().items(obj).error(true).build();
                    } // Si el monto es mayo a cero y como es menor al pago pactado por la validacion anterior
                    else if(amountToPay > 0) {
                        amountToPay = this._CalculatePay(listPayComplete, pay, contractPayDTO, amountToPay);
                    } // Si es monto es cero se termina el ciclo
                    else {

                        break;
                    }
                } // como el pago es despues de la fecha pactada se realiza el guardado directo
                else {
                    if(amountToPay > 0) {
                        amountToPay = this._CalculatePay(listPayComplete, pay, contractPayDTO, amountToPay);
                        obj.put("typesPay", appConfig.getPayEqualToAmount());
                    } else {
                        break;
                    }
                }
            }

            listPayComplete.stream().forEach(itemPay -> payRepository.save(itemPay));

            return ResponseDTO.builder().items(obj).message("Se guardo con éxito su pago").build();
        }
        return ResponseDTO.builder().error(true).message("No se encontro el registro").build();

    }
    public double _CalculatePay(ArrayList<ContractPay> listContractPay, ContractPay contractPay, ContractPayDTO contractPayDTO, double amountToPay) {
        double pendingToPay = contractPay.getAgreedPay() - contractPay.getPayAmount();
        double payAmount = 0;

        if(amountToPay > contractPay.getAgreedPay() && contractPay.getPayAmount() > 0) {
            payAmount = pendingToPay + contractPay.getPayAmount();
            amountToPay = amountToPay - pendingToPay;
        }
        else if(amountToPay < contractPay.getAgreedPay() && contractPay.getPayAmount() > 0) {
            payAmount = amountToPay + contractPay.getPayAmount();
            amountToPay = amountToPay - pendingToPay;
        }
        else if(amountToPay > contractPay.getAgreedPay() && contractPay.getPayAmount() == 0) {
            payAmount = contractPay.getAgreedPay();
            amountToPay = amountToPay - payAmount;
        }
        else if(amountToPay < contractPay.getAgreedPay() && contractPay.getPayAmount() == 0){
            payAmount = amountToPay;
            amountToPay = 0;
        }
        contractPay.setStatusPay(payAmount == contractPay.getAgreedPay() ? 1 : 0);
        contractPay.setCreatedNameUser(contractPayDTO.getCreatedNameUser());
        contractPay.setIdCreatedUser(contractPayDTO.getIdCreatedUser());
        contractPay.setPayDate(contractPayDTO.getPayDate());
        contractPay.setPayDidDate(contractPayDTO.getPayDidDate());
        contractPay.setPayFile(contractPayDTO.getPayFile());

        contractPay.setPayAmount(Math.round(payAmount * 100) / 100d);

        listContractPay.add(contractPay);
        return amountToPay;
    }
    public void _SaveAmortizationPays(ContractPayDTO contractPayDTO) {
        payRepository.save(new ContractPay(contractPayDTO));
    }
}
