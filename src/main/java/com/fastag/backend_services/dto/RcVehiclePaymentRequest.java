package com.fastag.backend_services.dto;


import lombok.Data;

@Data
public class RcVehiclePaymentRequest {
    private String chassisNumber;
    private String userId;
    private String service; // ONLY 3 TYPES
}
