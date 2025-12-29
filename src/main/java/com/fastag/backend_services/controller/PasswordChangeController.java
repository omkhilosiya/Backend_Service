package com.fastag.backend_services.controller;


import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.Repository.UserRepository;
import com.fastag.backend_services.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class PasswordChangeController {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;



    // THIS METHOD IS SPECIFY NEED TO CHANGE THE PASSWORD FROM ADMIN SIDE
    @PostMapping("/changethepassword")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> requestData) {

        String email = requestData.get("email");
        String rawPassword = requestData.get("password");
        String userole = requestData.get("role");

        if (!StringUtils.hasText(email) || !StringUtils.hasText(rawPassword)) {
            return ResponseEntity
                    .badRequest()
                    .body("Username and password are required");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Wallet wallet = walletRepository.findByUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        // role validation
        if(!"AGENT".equals(userole) && !"SUBAGENT".equals(userole)){
            return ResponseEntity
                    .badRequest()
                    .body("Invalid role");
        }

        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(userole);
        wallet.setType(userole);
        userRepository.save(user);
        walletRepository.save(wallet);

        requestData.put("password", "********");

        return ResponseEntity.ok(requestData);
    }

}
