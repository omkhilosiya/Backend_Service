package com.fastag.backend_services.dto;

import java.util.List;

public class LoginResponse {
    private String jwtToken;
    private String username;
    private List<String> roles;
    private DashboardResponse dashboardResponse;


    public LoginResponse(String username, List<String> roles, String jwtToken) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    public LoginResponse(String username, List<String> roles, String jwtToken, DashboardResponse dashboardResponse) {
        this.username = username;
        this.roles = roles;
        this.jwtToken = jwtToken;
        this.dashboardResponse = dashboardResponse;
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

    public DashboardResponse getDashboardResponse() {
        return dashboardResponse;
    }

    public void setUser(DashboardResponse dashboardResponse) {
        this.dashboardResponse = dashboardResponse;
    }
}
