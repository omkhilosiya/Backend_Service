package com.fastag.backend_services.service;


import com.fastag.backend_services.Model.Transaction;
import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.Repository.WalletRepository;
import com.fastag.backend_services.component.Authentication;
import com.fastag.backend_services.dto.RcVerifyPaymentRequest;
import com.fastag.backend_services.dto.RcVerifyRequest;
import com.fastag.backend_services.variables.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


@Service
public class PaysprintRcService {


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public String verifyRc(RcVerifyPaymentRequest rcVerifyPaymentRequest) {



        int amount;
        switch (rcVerifyPaymentRequest.getService()) {
            case "SMART_RC" -> amount = Variables.SMART_RC;
            case "PARIVAAN_RC" -> amount = Variables.PARIVAAN_RC;
            case "DIGI_LOCKER_RC" -> amount = Variables.DIGI_LOCKER_RC;
            case "DRIVING_LICENCE" -> amount = Variables.DRIVING_LICENCE_RC;
            case "VOTER_ID" -> amount = Variables.VOTER_ID;
            default -> throw new RuntimeException("Invalid service type");
        }

        // 2️⃣ Fetch wallet
        Wallet wallet = walletRepository.findByUserId(rcVerifyPaymentRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Your wallet balance is ₹0. Please recharge to continue."
            );
        }

        if (wallet.getBalance() < amount) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Insufficient balance. Required ₹" + amount +
                            ", Available ₹" + wallet.getBalance()
            );
        }


        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        // 3️⃣ Deduct balance
        wallet.setBalance(wallet.getBalance() - amount);
        wallet.setUpdatedAt(LocalDateTime.now());

        // 4️⃣ Create transaction (MATCHING YOUR MODEL)
        Transaction tx = new Transaction();
        tx.setType("DEBIT");
        tx.setAmount(amount);
        tx.setReason(rcVerifyPaymentRequest.getService());
        tx.setDeductedFor(rcVerifyPaymentRequest.getService());
        tx.setFromEntity(wallet.getEntityId());
        tx.setToEntity("PAYS_PRINT_VENDOR");
        tx.setPaymentId(UUID.randomUUID().toString());
        tx.setReferenceId(rcVerifyPaymentRequest.getVehicleNumber());
        tx.setNote(rcVerifyPaymentRequest.getService() + " RC verification");
        tx.setCreatedAt(LocalDateTime.now().toString());
        tx.setUpdatedAt(LocalDateTime.now().toString());

        if (wallet.getTransactions() == null) {
            wallet.setTransactions(new ArrayList<>());
        }
        wallet.getTransactions().add(tx);

        walletRepository.save(wallet);

        // 5️⃣ EXTERNAL RC API (UNCHANGED)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("secretKey", Variables.SECRET_KEY);
        headers.set("clientId", Variables.CLIENT_ID);

        RcVerifyRequest request = new RcVerifyRequest(rcVerifyPaymentRequest.getVehicleNumber());

        HttpEntity<RcVerifyRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(
                        Variables.INVINCIBLE_RC_URL,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

        return response.getBody();
    }


}