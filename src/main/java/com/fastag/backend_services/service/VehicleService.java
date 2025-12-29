package com.fastag.backend_services.service;

import com.fastag.backend_services.Model.Transaction;
import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.Repository.WalletRepository;
import com.fastag.backend_services.dto.RcVehiclePaymentRequest;
import com.fastag.backend_services.variables.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class VehicleService {

    private final RestTemplate restTemplate;

    @Autowired
    private WalletRepository walletRepository;

    public VehicleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getVehicleByChassis(RcVehiclePaymentRequest request) {

        int amount;

        switch (request.getService()) {
            case "SMART_RC" -> amount = Variables.SMART_RC;
            case "PARIVAAN_RC" -> amount = Variables.PARIVAAN_RC;
            case "DIGI_LOCKER_RC" -> amount = Variables.DIGI_LOCKER_RC;
            default -> throw new RuntimeException("Invalid service type");
        }

        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        wallet.setUpdatedAt(LocalDateTime.now());

        Transaction tx = new Transaction();
        tx.setType("DEBIT");
        tx.setAmount(amount);
        tx.setReason(request.getService());
        tx.setDeductedFor(request.getService());
        tx.setFromEntity(wallet.getEntityId());
        tx.setToEntity("PAYS_PRINT_VENDOR");
        tx.setPaymentId(UUID.randomUUID().toString());
        tx.setReferenceId(request.getChassisNumber());
        tx.setNote(request.getService() + " Vehicle Number Fetch");
        tx.setCreatedAt(LocalDateTime.now().toString());
        tx.setUpdatedAt(LocalDateTime.now().toString());

        if (wallet.getTransactions() == null) {
            wallet.setTransactions(new ArrayList<>());
        }
        wallet.getTransactions().add(tx);

        walletRepository.save(wallet);

        // 5️⃣ CALL EXTERNAL VENDOR API (UNCHANGED)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("clientId", Variables.CLIENT_ID);
        headers.set("secretKey", Variables.SECRET_KEY);

        Map<String, String> body = new HashMap<>();
        body.put("chassisNumber", request.getChassisNumber());

        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        Variables.REVERSE_RC,
                        entity,
                        String.class
                );

        return response.getBody();
    }

}


