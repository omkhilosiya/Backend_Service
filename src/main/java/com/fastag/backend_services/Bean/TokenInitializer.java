package com.fastag.backend_services.Bean;

import com.fastag.backend_services.Security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TokenInitializer implements CommandLineRunner {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void run(String... args) {
        String email = "system@fastag.com"; // you can replace this with any default user/email
        String token = jwtUtils.generateToken(email);

        System.out.println("\n============================");
        System.out.println("✅ Application Started Successfully!");
        System.out.println("🔑 Default JWT Bearer Token:");
        System.out.println("Bearer " + token);
        System.out.println("============================\n");
    }
}
