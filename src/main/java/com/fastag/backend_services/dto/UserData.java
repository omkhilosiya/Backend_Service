package com.fastag.backend_services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private String id;
    private String name;
    private String phone;
    private String email;
    private String entityId;
    private String joinedDate;
    private double walletBalance;
    private boolean status;
}
