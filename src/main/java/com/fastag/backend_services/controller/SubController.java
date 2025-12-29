package com.fastag.backend_services.controller;

import com.fastag.backend_services.component.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SubController {

    @Autowired
    private Authentication authentication;


    // OLD VENDER TOKEN API CURRENTLY USE LESS
    @GetMapping("/token")
    public String generateToken() {
        return authentication.getToken();
    }

}
