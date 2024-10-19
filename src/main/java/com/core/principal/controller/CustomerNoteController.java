package com.core.principal.controller;

import com.core.principal.dto.CustomerNoteDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.service.CustomerNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer-note")
public class CustomerNoteController {
    @Autowired
    private CustomerNoteService customerNoteService;

    @PostMapping("/save")
    public ResponseDTO SaveCustomerNote(@RequestBody CustomerNoteDTO customerNoteDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return  customerNoteService._SaveCustomerNote(customerNoteDTO);
        } catch (Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @GetMapping("/get-by-customer")
    public ResponseDTO GetByCustomer(@RequestParam(name = "id-customer") Long id) {
        ResponseDTO response = new ResponseDTO();
        try{
            return customerNoteService._GetByCustomer(id);
        } catch (Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
}
