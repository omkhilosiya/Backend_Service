package com.fastag.backend_services.component;

import com.fastag.backend_services.Model.User;
import java.util.List;

public class LoginResponse {
    private String jwtToken;
    private String username;
    private List<String> roles;
    private User user;


    public LoginResponse(String username, List<String> roles, String jwtToken) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    public LoginResponse(String username, List<String> roles, String jwtToken, User user) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
