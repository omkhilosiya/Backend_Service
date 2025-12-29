    package com.fastag.backend_services.controller;

    import com.fastag.backend_services.dto.RcVerifyPaymentRequest;
    import com.fastag.backend_services.service.PaysprintRcService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;



    @RestController
    public class RcController {

        @Autowired
        private PaysprintRcService rcService;

        // RC API THE FIRST API PROVIDED BY THE VENDOR
        @PostMapping("/rc/verify")
        public String verify(@RequestBody RcVerifyPaymentRequest rcVerifyPaymentRequest) {
            return rcService.verifyRc(rcVerifyPaymentRequest);
        }
    }
