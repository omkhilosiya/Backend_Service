package com.fastag.backend_services.service;


import com.fastag.backend_services.component.Authentication;
import com.fastag.backend_services.dto.RcVerifyRequest;
import com.fastag.backend_services.variables.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PaysprintRcService {

    @Autowired
    private RestTemplate restTemplate;

    public String verifyRc(String vehicleNumber) {

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("secretKey", Variables.SECRET_KEY);
        headers.set("clientId", Variables.CLIENT_ID);

        // Body
        RcVerifyRequest request = new RcVerifyRequest(vehicleNumber);

        HttpEntity<RcVerifyRequest> entity =
                new HttpEntity<>(request, headers);

        // API Call
        ResponseEntity<String> response =
                restTemplate.exchange(
                        Variables.INVINCIBLE_RC_URL,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        return response.getBody();
    }
}