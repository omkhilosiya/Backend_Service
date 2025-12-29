package com.fastag.backend_services.controller;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Repository.UserRepository;
import com.fastag.backend_services.dto.UserResponse;
import com.fastag.backend_services.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController

public class UserManagementController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    // GET ALL THE USERS IN WHERE THE TYPE ROLE IS SUBAGENTS
    @GetMapping("/getallusers")
    public ResponseEntity<Page<UserResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy
    ) {
        return ResponseEntity.ok(userService.getUsers(page, size, sortBy));
    }









}
