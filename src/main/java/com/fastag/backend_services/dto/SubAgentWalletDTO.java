package com.fastag.backend_services.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubAgentWalletDTO {
    private String username;
    private String userId;
    private double balance;
}
