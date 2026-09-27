package com.ecotracker.profile.controller;

import com.ecotracker.profile.model.User;
import com.ecotracker.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/me")
    public User getMyProfile(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return profileService.getUser(userId);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal == #id")
    public User getUser(@PathVariable Long id) {
        return profileService.getUser(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return profileService.getAllUsers();
    }
}
