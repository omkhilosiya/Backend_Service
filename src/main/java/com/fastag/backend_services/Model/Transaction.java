package com.fastag.backend_services.Model;

import lombok.Data;

@Data
public class Transaction {
    private Breakdown breakdown;
    private String type;
    private double amount;
    private String reason;
    private String deductedFor;
    private String fromEntity;
    private String toEntity;
    private String paymentId;
    private String referenceId;
    private String note;
    private Metadata metadata;
    private String id;
    private String createdAt;
    private String updatedAt;
}