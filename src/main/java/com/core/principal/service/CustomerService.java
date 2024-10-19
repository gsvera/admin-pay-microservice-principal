package com.core.principal.service;

import com.core.principal.dto.CustomerDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.entity.Customer;
import com.core.principal.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public ResponseDTO _SaveCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = customerRepository.save(new Customer(customerDTO));
        if(newCustomer != null) {
            return ResponseDTO.builder().error(false).items(newCustomer.getId()).message("Registro guardado con exito").build();
        }
        else {
            return ResponseDTO.builder().error(true).message("No se pudo guardar el registro").build();
        }
    }

    public ResponseDTO _UpdateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.getId()).orElseThrow();
        if(customer != null) {
            customer.setFirstName(customerDTO.getFirstName());
            customer.setLastName(customerDTO.getLastName());
            customer.setEmail(customerDTO.getEmail());
            customer.setCelPhone(customerDTO.getCelPhone());
            customer.setPhoneOffice(customerDTO.getPhoneOffice());
            customer.setDescription(customerDTO.getDescription());
            customer.setUpdatedUser(customerDTO.getUpdatedUser());
            customer.setCelPhoneWhatsapp(customerDTO.isCelPhoneWhatsapp());
            customer.setPhoneOfficeWhatsapp(customerDTO.isPhoneOfficeWhatsapp());
            customerRepository.save(customer);

            return ResponseDTO.builder().message("Cliente actualizado correctamente").build();
        } else {
            return  ResponseDTO.builder().error(true).message("No se encontre el usuario para actualizar").build();
        }
    }

    public ResponseDTO _GetFilterData(int page, int size, String codeCompany, String word) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Customer> list = customerRepository.findAll(codeCompany, word, pageRequest);
        return ResponseDTO.builder().error(false).items(list).build();
    }

    public ResponseDTO _GetFilterAllData(String value) {
        if(value.isEmpty()){
            return ResponseDTO.builder().message("El valor no debe estar vacio").build();
        } else {
            List<Customer> listCustomer = customerRepository.findAllData(value);
            return ResponseDTO.builder().items(listCustomer.stream().map(item -> new CustomerDTO(item)).collect(Collectors.toList())).build();
        }
    }
    public ResponseDTO _GetById(long idCustomer) {
        CustomerDTO customerDTO = new CustomerDTO(customerRepository.getReferenceById(idCustomer));
        if(customerDTO != null) {
            return ResponseDTO.builder().error(false).items(customerDTO).build();
        } else {
          return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
        }
    }

    public ResponseDTO _DeleteCustomer(Long idCustomer, String codeCompany) {
        Customer customer = customerRepository.getReferenceById(idCustomer);

        // ************ PENDIENTE AGREGAR LOGICA PARA VALIDAR SI CUENTA CON ADEUDO PENDIENTE *****************

        if(customer != null && codeCompany.equalsIgnoreCase(customer.getCodeCompany())) {
            customer.setDeleteRegister(true);
            customerRepository.save(customer);
            return ResponseDTO.builder().message("Se elemino el registro correctamente").build();
        } else {
            return ResponseDTO.builder().error(true).message("No se encotro el registro").build();
        }
    }
}
