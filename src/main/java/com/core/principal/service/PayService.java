package com.core.principal.service;

import com.core.principal.config.AppConfig;
import com.core.principal.dto.ContractPayDTO;
import com.core.principal.dto.CustomerNoteDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.entity.Contract;
import com.core.principal.entity.ContractPay;
import com.core.principal.entity.Pay;
import com.core.principal.repository.ContractPayRepository;
import com.core.principal.repository.ContractRepository;
import com.core.principal.repository.PayContractRepository;
import com.core.principal.repository.PayRepository;
import com.core.principal.utils.GeneralUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class PayService {
    private final PayContractRepository payContractRepository;
    private final PayRepository payRepository;
    private final ContractRepository contractRepository;
    private final ContractPayRepository contractPayRepository;
    private final CustomerNoteService customerNoteService;
    private final AppConfig appConfig;
    public ResponseDTO _SavePay(ContractPayDTO contractPayDTO, boolean force) {
        Contract contract = contractRepository.findByIdContract(Long.valueOf(contractPayDTO.getIdContractDto()));
        if(contract != null) {
            double amountToPay = contractPayDTO.getPayAmount();
            ArrayList<ContractPay> listPays = payContractRepository.findByIdContractPaysPending(contractPayDTO.getIdContractDto());

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
            // Guardar el calculo de los pagos realizados a la amortizacion
            payContractRepository.saveAll(listPayComplete);

            String idsPay = listPayComplete.stream().map(itemPay -> itemPay.getId().toString()).collect(Collectors.joining(","));

            // Guardar el registro del pago completo
            this._SavePay(contractPayDTO, idsPay, contract);

            // Actualizar estatus del contrato
            this._ValidFinalContract(contract.getId());

            // Agregar nota al cliente por el pago realizado
            this._SavePayNote(contractPayDTO, contract.getIdCustomer(), contract.getFolio());

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
            double differentPay = (amountToPay <= pendingToPay ? amountToPay : pendingToPay);
            payAmount = differentPay + contractPay.getPayAmount();
            amountToPay = amountToPay - differentPay;
        }
        else if(amountToPay > contractPay.getAgreedPay() && contractPay.getPayAmount() == 0) {
            payAmount = contractPay.getAgreedPay();
            amountToPay = amountToPay - payAmount;
        }
        else if(amountToPay < contractPay.getAgreedPay() && contractPay.getPayAmount() == 0){
            payAmount = amountToPay;
            amountToPay = 0;
        }
        contractPay.setStatusPay(payAmount >= contractPay.getAgreedPay() ? 1 : 0);
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
        payContractRepository.save(new ContractPay(contractPayDTO));
    }
    public void _SavePay(ContractPayDTO contractPayDTO, String idsPay, Contract contract) {
        Pay newPayRegister = new Pay(contractPayDTO);
        newPayRegister.setIdsPayContract(idsPay);
        newPayRegister.setIdContractXPay(contract);
        payRepository.save(newPayRegister);
    }

    public void _SavePayNote(ContractPayDTO contractPayDTO, int idCustomer, String folio) {
        CustomerNoteDTO newCustomerNoteDto = new CustomerNoteDTO(
                (long)idCustomer,
                contractPayDTO.getIdCreatedUser(),
                contractPayDTO.getCreatedNameUser(),
                "Se realiza pago por el monto de <i>" + GeneralUtils.convertCurrency(contractPayDTO.getPayAmount()) + "</i> con fecha de <i>" + contractPayDTO.getPayDidDate() + "</i> al contrato <i>" + folio + "</i>. <br><i>*Nota generada por sistema*</i>"
        );
        customerNoteService._SaveCustomerNote(newCustomerNoteDto);
    }

    public void _ValidFinalContract(Long idContract) {
        List<Pay> listPay = payRepository.findByIdContract(idContract);
        List<ContractPay> listContractPay = contractPayRepository.findByIdContract(idContract);

        // **********       REFACTORIZAR ESTA FUNCIONA PARA QUE SE VALIDE LOS ESTATUS QUE SE DEBEN APLICAR AL CONTRATO

        double totalPay = listPay.stream().mapToDouble(Pay::getPayAmount).sum();
        double totalContractPay = listContractPay.stream().mapToDouble(ContractPay::getAgreedPay).sum();

        if(totalPay >= totalContractPay) {
            contractRepository.updateStatusContract(7,idContract);
        }
    }

    public ResponseDTO _DeletePay(Long idPay, Long idContract) {
        Pay pay = payRepository.findById(idPay).orElseThrow();
        List<ContractPay> listContractPay = contractPayRepository.findByIdContract(idContract);

        listContractPay.sort(Comparator.comparing(ContractPay::getId).reversed());
        final double[] amount = {pay.getPayAmount()};

        for(ContractPay contractPay : listContractPay) {
            double montoPagado = contractPay.getPayAmount();
            if(amount[0] == 0) {
                break;
            }
            if(contractPay.getPayAmount() != 0) {
                if(montoPagado < amount[0]) {
                    contractPay.setPayAmount(0);
                    amount[0] = amount[0] - montoPagado;
                } else if (montoPagado > amount[0]) {
                    contractPay.setPayAmount(montoPagado - amount[0]);
                    amount[0] = 0;
                }
            }
        }

        payContractRepository.saveAll(listContractPay);
        payRepository.delete(pay);

        return ResponseDTO.builder().message("Se ha eliminado correctamente el pago").build();
    }
}
