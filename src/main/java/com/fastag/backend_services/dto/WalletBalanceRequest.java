package com.fastag.backend_services.dto;

import lombok.Data;

@Data
public class WalletBalanceRequest {
    private String userId;
    private Double amount;
}
