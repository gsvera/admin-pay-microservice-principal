package com.core.principal.controller;

import com.core.principal.dto.CustomerDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @PostMapping("/save")
    public ResponseDTO SaveCustomer(@RequestBody CustomerDTO customerDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return customerService._SaveCustomer(customerDTO);
        } catch (Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @PutMapping("/update")
    public ResponseDTO UpdateCustomer(@RequestBody CustomerDTO customerDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return customerService._UpdateCustomer(customerDTO);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
        }
        return response;
    }
    @GetMapping("/filter/data")
    public ResponseDTO GetByFilterData(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(name = "code-company") String codeCompany,
            @RequestParam(required = false) String word
    ) {
        ResponseDTO response = new ResponseDTO();
        try{
            return customerService._GetFilterData(page, size, codeCompany, word); // Falta probar que funcione
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @GetMapping("/filter/all-data")
    public ResponseDTO GetFilterAllData(@RequestParam String value) {
        ResponseDTO response = new ResponseDTO();
        try{
            return customerService._GetFilterAllData(value);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @GetMapping("/get-by-id")
    public ResponseDTO GetById(@RequestParam(name = "idcustomer") long idCustomer) {
        ResponseDTO response = new ResponseDTO();
        try{
            return customerService._GetById(idCustomer);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @PostMapping("/delete")
    public ResponseDTO DeleteCustomer(@RequestParam(name = "idcustomer") Long idCustomer, @RequestParam(name = "code-company") String codeCompany) {
        ResponseDTO response = new ResponseDTO();
        try{
            return customerService._DeleteCustomer(idCustomer, codeCompany);
        } catch (Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
}
