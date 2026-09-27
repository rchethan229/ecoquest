package com.ecotracker.effort.client;

import com.ecotracker.effort.model.Effort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "reward-service")
public interface RewardClient {

    @PostMapping("/rewards/{userId}/points")
    void addPoints(
            @PathVariable("userId") Long userId,
            @RequestParam("points") int points);
}
