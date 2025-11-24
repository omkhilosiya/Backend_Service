package com.fastag.backend_services.controller;


import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PasswordChangeController {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private UserRepository userRepository;


    @PostMapping("/updatepassword")
    public ResponseEntity<User> updatePassword(@RequestBody Map<String, String> requestData) {

        String userId = requestData.get("userId");
        String rawPassword = requestData.get("password");

        User user = userRepository.findByUserId(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        String encryptedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encryptedPassword);
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
