package com.ecotracker.effort.controller;

import com.ecotracker.effort.model.Effort;
import com.ecotracker.effort.service.EffortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/efforts")
public class EffortController {

    @Autowired
    private EffortService effortService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Effort addEffort(@RequestBody Effort effort, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return effortService.addEffort(effort, userId);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Effort> getMyEfforts(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return effortService.getEffortsByUser(userId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal == #userId")
    public List<Effort> getEffortsByUser(@PathVariable Long userId) {
        return effortService.getEffortsByUser(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Effort> getAllEfforts() {
        return effortService.getAllEfforts();
    }
}
