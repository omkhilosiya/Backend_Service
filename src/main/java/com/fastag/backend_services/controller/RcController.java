package com.fastag.backend_services.controller;

import com.fastag.backend_services.dto.RcVerifyRequest;
import com.fastag.backend_services.service.PaysprintRcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/rc")
public class RcController {


    @Autowired
    private PaysprintRcService rcService;

    @GetMapping("/verify")
    public String verify(@RequestParam String vehicleNumber) {
        return rcService.verifyRc(vehicleNumber);
    }
}
