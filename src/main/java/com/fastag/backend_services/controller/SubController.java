package com.fastag.backend_services.controller;

import com.fastag.backend_services.component.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubController {

    @Autowired
    private Authentication authentication;

    @GetMapping("/token")
    public String generateToken() {
        return authentication.getToken();
    }

}
