package com.core.principal.controller;

import com.core.principal.dto.LoginRequestDTO;
import com.core.principal.dto.UserDTO;
import com.core.principal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.core.principal.dto.ResponseDTO;

@RestController
@RequestMapping("/api/public")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/prueba")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO prueba() {
        return new ResponseDTO();
    }
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO SaveUser(@RequestBody UserDTO userDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return userService._SaveUser(userDTO);
        } catch (Exception ex) {
            response.error = true;
//            response.message = ex.getMessage();
            System.out.println(ex.getMessage());
            response.message = "Ocurrio un error intentelo mas tarde";
        }
        return response;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO LoginUser(@RequestBody LoginRequestDTO loginRequestDTO) {
        ResponseDTO response = new ResponseDTO();
        try{
            return userService._LoginUser(loginRequestDTO);
        } catch(Exception ex) {
            response.error = true;
//            response.message = ex.getMessage();
            System.out.println(ex.getMessage());
            response.message = "Usuario y/o contraseña incorrecto";
        }
        return response;
    }
}
