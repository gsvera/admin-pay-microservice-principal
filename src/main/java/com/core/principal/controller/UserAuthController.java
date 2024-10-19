package com.core.principal.controller;

import com.core.principal.dto.ResponseDTO;
import com.core.principal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/user")
public class UserAuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/logout")
    public ResponseDTO LogoutUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        ResponseDTO response = new ResponseDTO();
        try{
            System.out.println(token.substring(7));
            return userService._Logout(token.substring(7));
        } catch(Exception ex) {
            response.error = true;
            System.out.println(ex.getMessage());
            response.message = "Ocurrio un error, intentelo mas tarde";
        }
        return  response;
    }
}
