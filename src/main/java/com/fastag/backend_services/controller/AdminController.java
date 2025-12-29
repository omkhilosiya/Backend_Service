package com.fastag.backend_services.controller;

import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.dto.SubAgentWalletDTO;
import com.fastag.backend_services.dto.WalletBalanceRequest;
import com.fastag.backend_services.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    private UserWalletService userWalletService;

    // THIS METHOD IS ALSO GIVEN TO ADMIN , WHERE INTIALLY ADDING THE BALANCE THOSE DETAILS WILL POP UP OF THE SUBAGENT
    @GetMapping("/subagents")
    public List<SubAgentWalletDTO> getSubAgents() {
        return userWalletService.getSubAgentsWithBalance();
    }


    // ADDING THE BALANCE USING TRANSTIONAL METHOD
    @PostMapping("/add-balance")
    public ResponseEntity<?> addBalance(@RequestBody WalletBalanceRequest request) {

        Wallet updatedWallet = userWalletService.addBalance(
                request.getUserId(),
                request.getAmount(),
                request.getNote()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "Balance added successfully",
                        "userId", updatedWallet.getUserId(),
                        "balance", updatedWallet.getBalance(),
                        "transactions", updatedWallet.getTransactions()
                )
        );
    }


}
