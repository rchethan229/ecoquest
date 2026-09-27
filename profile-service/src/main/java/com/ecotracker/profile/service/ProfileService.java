package com.ecotracker.profile.service;

import com.ecotracker.profile.dto.AuthResponse;
import com.ecotracker.profile.dto.LoginRequest;
import com.ecotracker.profile.dto.SignupRequest;
import com.ecotracker.profile.model.User;
import com.ecotracker.profile.repository.ProfileRepository;
import com.ecotracker.profile.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {

    @Autowired private ProfileRepository profileRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    public User signup(SignupRequest request) {
        String email = request.getEmail().trim().toLowerCase();

        if (profileRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered. Please use another email.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLocation(request.getLocation());
        // Public signup can create USER accounts only.
        user.setRole("USER");

        try {
            return profileRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Email already registered. Please use another email.");
        }
    }

    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        User user = profileRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getLocation(), user.getRole());
    }

    public User getUser(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return profileRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return profileRepository.findByEmail(email).orElse(null);
    }
}
