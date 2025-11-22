package com.fastag.backend_services.controller;

import com.fastag.backend_services.Model.User;
import com.fastag.backend_services.Model.Wallet;
import com.fastag.backend_services.Repository.UserRepository;
import com.fastag.backend_services.Repository.WalletRepository;
import com.fastag.backend_services.component.*;
import com.fastag.backend_services.dto.DashboardResponse;
import com.fastag.backend_services.dto.LoginRequest;
import com.fastag.backend_services.dto.LoginResponse;
import com.fastag.backend_services.variables.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class Signcontroller {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello";
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String userEndpoint(){
        return "Hello, User!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint(){
        return "Hello, Admin!";
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        User user = userRepository.findByUsername(userDetails.getUsername());
        Optional<Wallet> walletOpt = walletRepository.findByUserId(user.getId());
        Wallet wallet = walletOpt.orElse(null);
        DashboardResponse newDashBoardResponse = CommonMethod.updateTheWallet(wallet);
            LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken ,newDashBoardResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Username already exists!");
            map.put("status", false);
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        User user = CommonMethod.addAlltheDetails(request,passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        Wallet wallet = CommonMethod.createTheWallet(user);
        walletRepository.save(wallet);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());



        LoginResponse response = new LoginResponse(
                userDetails.getUsername(),
                roles,
                jwtToken
        );
        return ResponseEntity.ok(response);
    }

}
