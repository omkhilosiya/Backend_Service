package com.fastag.backend_services.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "wallets")
public class Wallet {
    @Id
    private String id;
    private String userId;
    private String entityId;
    private String type;
    private double balance;
    private List<Transaction> transactions;
    private LocalDateTime createdAt;
    private LocalDateTime  updatedAt;

}