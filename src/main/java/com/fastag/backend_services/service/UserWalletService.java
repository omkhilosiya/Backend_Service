package com.fastag.backend_services.service;

import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.Repository.WalletRepository;
import com.fastag.backend_services.dto.SubAgentWalletDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Wallet addBalance(String userId, Double amount) {

        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for userId: " + userId));

        double updatedBalance = wallet.getBalance() + amount;
        wallet.setBalance(updatedBalance);

        return walletRepository.save(wallet);
    }


}
