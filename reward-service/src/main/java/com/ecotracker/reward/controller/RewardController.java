package com.ecotracker.reward.controller;

import com.ecotracker.reward.model.Reward;
import com.ecotracker.reward.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardController {

    @Autowired private RewardService rewardService;

    @PostMapping("/{userId}/points")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal == #userId")
    public Reward addPoints(@PathVariable Long userId, @RequestParam int points) {
        return rewardService.addPoints(userId, points);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Reward getMyReward(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return rewardService.getReward(userId);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or authentication.principal == #userId")
    public Reward getReward(@PathVariable Long userId) {
        return rewardService.getReward(userId);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Reward> getAllRewards() {
        return rewardService.getAllRewards();
    }
}
