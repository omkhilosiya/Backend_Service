package com.fastag.backend_services.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class VehicleService {

    private final RestTemplate restTemplate;

    public VehicleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getVehicleByChassis(String chassisNumber) {
        String url = "https://api.invincibleocean.com/invincible/vehicleByChassisLive";

        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", "YOUR_CLIENT_ID");  // Replace with actual clientId
        headers.set("secretKey", "YOUR_SECRET_KEY"); // Replace with actual secretKey

        // Prepare body
        Map<String, String> body = new HashMap<>();
        body.put("chassisNumber", chassisNumber);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        return response.getBody();
    }
}

