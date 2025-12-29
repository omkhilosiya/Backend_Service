package com.fastag.backend_services.service;

import com.fastag.backend_services.Model.Metadata;
import com.fastag.backend_services.Model.Transaction;
import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.Repository.WalletRepository;
import com.fastag.backend_services.dto.SubAgentWalletDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserWalletService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private WalletRepository walletRepository;

    public List<SubAgentWalletDTO> getSubAgentsWithBalance() {


        Aggregation aggregation = Aggregation.newAggregation(

                Aggregation.lookup(
                        "wallet",   // wallet collection
                        "userId",    // hahaha.userId
                        "userId",    // wallets.userId
                        "wallet"
                ),

                Aggregation.unwind("wallet"),
                Aggregation.match(
                        Criteria.where("wallet.type").is("SUBAGENT")
                ),
                Aggregation.project()
                        .and("username").as("username")
                        .and("userId").as("userId")
                        .and("wallet.balance").as("balance")
        );
        return mongoTemplate.aggregate(
                aggregation,
                "user",
                SubAgentWalletDTO.class
        ).getMappedResults();
    }

    @Transactional
    public Wallet addBalance(String userId, Double amount, String note) {

        if (amount == null || amount <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Amount must be greater than zero"
            );
        }

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Wallet not found for userId: " + userId
                ));

        // Update balance
        wallet.setBalance(wallet.getBalance() + amount);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setType("CREDIT");
        transaction.setAmount(amount);
        transaction.setReason("Balance added successfully By Admin");
        transaction.setNote(note != null ? note : "Wallet top-up");
        transaction.setFromEntity("SYSTEM");
        transaction.setToEntity(userId);
        transaction.setCreatedAt(LocalDateTime.now().toString());
        transaction.setUpdatedAt(LocalDateTime.now().toString());

        Metadata metadata = new Metadata();
        metadata.setRcType("WALLET_TOPUP");
        transaction.setMetadata(metadata);

        if (wallet.getTransactions() == null) {
            wallet.setTransactions(new ArrayList<>());
        }
        wallet.getTransactions().add(transaction);

        return walletRepository.save(wallet);
    }
}
