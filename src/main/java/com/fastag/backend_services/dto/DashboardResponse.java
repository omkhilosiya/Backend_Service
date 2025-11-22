package com.fastag.backend_services.dto;

import com.fastag.backend_services.Model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponse {
    private boolean success;
    private String currency;
    private Wallet walletDetails;
    private LocalDateTime LastUpdated;
}
