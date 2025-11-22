package com.fastag.backend_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentResponse {
    private String username;
    private String phoneNumber;
    private LocalDateTime joinedDate;
    private double walletBalance;
    private boolean download = false;
    private boolean activeStatus;
}

