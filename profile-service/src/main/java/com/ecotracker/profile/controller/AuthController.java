package com.ecotracker.profile.controller;

import com.ecotracker.profile.dto.AuthResponse;
import com.ecotracker.profile.dto.LoginRequest;
import com.ecotracker.profile.dto.SignupRequest;
import com.ecotracker.profile.model.User;
import com.ecotracker.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            User user = profileService.signup(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Signup successful. Your profile was created automatically.",
                    "userId", user.getId(),
                    "email", user.getEmail()
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("message", ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = profileService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage()));
        }
    }
}
