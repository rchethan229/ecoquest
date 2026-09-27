package com.ecotracker.profile.dto;

public class AuthResponse {
    private String token;
    private Long userId;
    private String name;
    private String email;
    private String location;
    private String role;

    public AuthResponse(String token, Long userId, String name, String email, String location, String role) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.location = location;
        this.role = role;
    }

    public String getToken() { return token; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getLocation() { return location; }
    public String getRole() { return role; }
}
