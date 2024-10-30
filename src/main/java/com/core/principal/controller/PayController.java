package com.core.principal.controller;

import com.core.principal.dto.ContractPayDTO;
import com.core.principal.dto.ResponseDTO;
import com.core.principal.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pay")
public class PayController {
    @Autowired
    private PayService payService;
    @PostMapping("/save-pay")
    public ResponseDTO SavePay(@RequestParam(required = false, defaultValue = "false") boolean force, @RequestBody ContractPayDTO contractPayDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return payService._SavePay(contractPayDTO, force);
        } catch(Exception ex) {
            response.error = true;
            response.message = "Ocurrio un error, intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return response;
    }
}
