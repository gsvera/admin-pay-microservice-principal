package com.core.principal.controller;

import com.core.principal.dto.ContractDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping("/get-by-customer")
    public ResponseDTO GetByCustomer(@RequestParam(name = "id-customer") Long idCustomer) {
        ResponseDTO response = new ResponseDTO();
        try{
            return contractService._GetByCustomer(idCustomer);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @GetMapping("/get-by-id")
    public ResponseDTO GetById(@RequestParam(name="id-contract") Long idContract) {
        ResponseDTO response = new ResponseDTO();
        try{
            return contractService._GetById(idContract);
        } catch (Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @GetMapping("/get-by-customer-contract")
    public ResponseDTO GetByCustomerContract(@RequestParam String word) {
        ResponseDTO response = new ResponseDTO();
        try{
            return contractService._GetByCustomerContract(word);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @GetMapping("/get-contract-with-pays")
    public ResponseDTO GetContractWithPays(@RequestParam Long id) {
        ResponseDTO response = new ResponseDTO();
        try{
            return contractService._GetContractWithPays(id);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @PostMapping("/save")
    public ResponseDTO SaveContract(@RequestBody ContractDTO contractDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return contractService._Save(contractDTO);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @PutMapping("/update")
    public ResponseDTO UpdateContract(@RequestBody ContractDTO contractDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return  contractService._Update(contractDTO);
        } catch (Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @PostMapping("/init-contract")
    public ResponseDTO InitContract(@RequestBody ContractDTO contractDTO) {
        ResponseDTO response = new ResponseDTO();
        try {
            return contractService._InitContract(contractDTO);
        } catch(Exception ex){
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
    @PostMapping("/delete")
    public ResponseDTO DeleteCotract(@RequestParam(name = "id-contract") Long idContract) {
        ResponseDTO response = new ResponseDTO();
        try {
            return contractService._DeleteContract(idContract);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
}
