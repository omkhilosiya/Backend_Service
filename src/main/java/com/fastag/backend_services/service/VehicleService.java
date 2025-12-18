package com.fastag.backend_services.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.fastag.backend_services.variables.Variables.*;

@Service
public class VehicleService {

    private final RestTemplate restTemplate;

    public VehicleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getVehicleByChassis(String chassisNumber) {

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", CLIENT_ID);
        headers.set("secretKey", SECRET_KEY);

        Map<String, String> body = new HashMap<>();
        body.put("chassisNumber", chassisNumber);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(REVERSE_RC, entity, String.class);
        return response.getBody();
    }
}

