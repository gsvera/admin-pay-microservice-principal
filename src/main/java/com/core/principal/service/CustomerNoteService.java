package com.core.principal.service;

import com.core.principal.dto.CustomerNoteDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.entity.CustomerNote;
import com.core.principal.repository.CustomerNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CustomerNoteService {
    private final CustomerNoteRepository customerNoteRepository;

    public ResponseDTO _SaveCustomerNote(CustomerNoteDTO customerNoteDTO) {

        CustomerNote customerNote = customerNoteRepository.save(new CustomerNote(customerNoteDTO));

        if(customerNote != null) {
            return ResponseDTO.builder().message("Se guardo la nota con éxito").build();
        } else {
            return ResponseDTO.builder().error(true).message("No se pudo guardar la nota").build();
        }
    }
    public ResponseDTO _GetByCustomer(Long id) {
        ArrayList<CustomerNote> customerList = customerNoteRepository.findByIdCustomer(id);
        return ResponseDTO.builder().items(
                customerList.stream().map(item -> new CustomerNote(item.getIdCustomer(), item.getIdUser(), item.getNameUserCreated(), item.getNote(), item.getCreatedAt())).collect(Collectors.toList())
        ).build();
    }
}
