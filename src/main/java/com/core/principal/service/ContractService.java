package com.core.principal.service;

import com.core.principal.config.AppConfig;
import com.core.principal.dto.ContractDTO;
import com.core.principal.dto.CustomerNoteDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.entity.Config;
import com.core.principal.entity.Contract;
import com.core.principal.repository.ConfigRepository;
import com.core.principal.repository.ContractRepository;
import com.core.principal.utils.GeneralUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ContractService {
    private final ContractRepository contractRepository;
    private final ConfigRepository configRepository;
    private final AppConfig appConfig;
    private final CustomerNoteService customerNoteService;
    public ResponseDTO _Save(ContractDTO contractDTO) {
        Contract contract = contractRepository.save(new Contract(contractDTO));
        if(contract != null) {
            return ResponseDTO.builder().message("Se guardo el contrato con éxito").build();
        }else {
            return ResponseDTO.builder().error(true).message("No se pudo guardar el contrato").build();
        }
    }
    public ResponseDTO _Update(ContractDTO contractDTO) {
        Contract contract = contractRepository.findByIdContract(contractDTO.getId());
        if(contract != null) {
            this._UpdateContract(contract, contractDTO);
            contractRepository.save(contract);

            return ResponseDTO.builder().message("Se ha actualizado el contracto con éxito").build();
        }
        return ResponseDTO.builder().error(true).message("No se pudo actualizar el contrato").build();
    }
    public ResponseDTO _InitContract(ContractDTO contractDTO) {
        Config config = configRepository.findByConfigCode(appConfig.getAppCodeFolio());
        if(config != null) {
            String folio = config.getConfigValueString() + config.getConfigValueInt();
            if(contractDTO.getId() != null) {
                Contract contract = contractRepository.findByIdContract(contractDTO.getId());
                contract.setFolio(folio);
                this._UpdateContract(contract, contractDTO);
            } else {
                contractDTO.setFolio(folio);
                contractRepository.save(new Contract(contractDTO));
            }
            config.setConfigValueInt(config.getConfigValueInt()+1);
            configRepository.save(config);

            String currencyAmount = GeneralUtils.convertCurrency(contractDTO.getAmount());

            customerNoteService._SaveCustomerNote(
                    CustomerNoteDTO.builder()
                        .idCustomer(contractDTO.getIdCustomer())
                        .idUser(contractDTO.getIdCreatedUser())
                        .nameUserCreated(contractDTO.getNameUserCreated())
                        .note("Se ha generado un contrato con folio: " + folio + " por el monto de " + currencyAmount + " con fecha de inicio " + contractDTO.getStartDate() + ". <br><i>*Nota generada por sistema*</i>")
                        .build());

            return ResponseDTO.builder().message("Se ha inicializado el contrato con exito").build();
        }else {
            return ResponseDTO.builder().error(true).message("No se encontro la configuración del folio").build();
        }
    }
    private void _UpdateContract(Contract contract, ContractDTO contractDTO) {
        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());
        contract.setTypeAmortization(contractDTO.getTypeAmortization());
        contract.setPeriod(contractDTO.getPeriod());
        contract.setNumberPeriod(contractDTO.getNumberPeriod());
        contract.setAmount(contractDTO.getAmount());
        contract.setInterestRate(contractDTO.getInterestRate());
        contract.setInterest(contractDTO.getInterest());
        contract.setStatusContract(contractDTO.getStatusContract());
        contractRepository.save(contract);
    }
    public ResponseDTO _GetByCustomer(Long idCustomer) {
        ArrayList<Contract> listContract = contractRepository.findByCustomer(idCustomer);

        return new ResponseDTO().builder().items(listContract.stream().map(item -> new ContractDTO(item)).collect(Collectors.toList())).build();
    }

    public ResponseDTO _GetById(Long idContract) {
        Contract contract = contractRepository.findByIdContract(idContract);

        return ResponseDTO.builder().items(new ContractDTO(contract)).build();
    }

    public ResponseDTO _DeleteContract(Long idContract) {
        Contract contract = contractRepository.findByIdContract(idContract);
        // SE IGUALA A 1 POR QUE ES ES VALOR DE CUANDO UN CONTRATO ESTA EN BORRADOR
        if(contract != null && contract.getStatusContract() == 1) {
            contractRepository.delete(contract);
            return ResponseDTO.builder().message("Se elimino el contrato exitosamente").build();
        }
        return ResponseDTO.builder().error(true).message("No se posible eliminar el contrato").build();
    }
}