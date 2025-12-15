package com.fastag.backend_services.component;

import lombok.Data;


@Data
public class SignupRequest {
    private String username;
    private String password;
    private String phoneNo;
    private String email;
}
