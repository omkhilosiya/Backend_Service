package com.fastag.backend_services.controller;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserManagementController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/usermanagement")
    public ResponseEntity<User> createnewsubAgent(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        User newUser = new User();
        if(StringUtils.hasText(newUser.getPassword())){
            newUser.setPassword(encryptedPassword);
        }
        return ResponseEntity.ok(newUser);
    }

}
