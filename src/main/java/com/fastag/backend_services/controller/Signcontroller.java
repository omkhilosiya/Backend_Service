package com.fastag.backend_services.controller;

import com.fastag.backend_services.GlobalExceptionHandler.UserAlreadyExistsException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // LOGIN
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("----step 1----- achieved ----------");
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("----step 2 ----- achieved ----------");
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        User userOpt = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("----step 3 ----- achieved ----------");
        Wallet wallet = walletRepository
                .findByUserId(userOpt.getUserId())
                .orElse(null);
        DashboardResponse newDashboardResponse =
                CommonMethod.updateTheWallet(wallet);
        System.out.println("----step 4 ----- achieved ----------");
        if (!userOpt.isStatus()) {
            userOpt.setStatus(true);
            userRepository.save(userOpt);
        }
        LoginResponse response = new LoginResponse(
                userDetails.getUsername(),
                roles,
                jwtToken,
                newDashboardResponse
        );

        return ResponseEntity.ok(response);
    }

    //REGISTER
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        User user = CommonMethod.addAlltheDetails(request,passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        Wallet wallet = CommonMethod.createTheWallet(user);
        walletRepository.save(wallet);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
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
