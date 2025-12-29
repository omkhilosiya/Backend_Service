package com.fastag.backend_services.controller;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequestMapping("/user")
@RestController
public class LogoutController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        System.out.println("Logout Request For: " + username);

        Optional<User> userOpt = userRepository.findByEmail(username);

        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();
        user.setStatus(false);

        userRepository.save(user);

        return ResponseEntity.ok("User set to INACTIVE");
    }

}
