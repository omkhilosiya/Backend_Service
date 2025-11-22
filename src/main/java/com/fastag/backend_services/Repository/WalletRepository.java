package com.fastag.backend_services.Repository;

import com.fastag.backend_services.Model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    Optional<Wallet> findByUserId(String userId);
    // find wallet by same userId string
    // get all wallets
    List<Wallet> findAll();
}
