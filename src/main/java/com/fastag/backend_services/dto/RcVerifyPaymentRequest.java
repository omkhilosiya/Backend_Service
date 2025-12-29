package com.fastag.backend_services.dto;

import lombok.Data;

@Data
public class RcVerifyPaymentRequest {
    private String vehicleNumber;
    private String userId;
    private String service;
}
