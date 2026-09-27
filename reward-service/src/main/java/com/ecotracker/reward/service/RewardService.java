package com.ecotracker.reward.service;

import com.ecotracker.reward.model.Reward;
import com.ecotracker.reward.repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

    @Autowired
    private RewardRepository rewardRepository;

    public Reward addPoints(Long userId, int points) {

        Reward reward = rewardRepository.findByUserId(userId).orElse(null);

        if (reward == null) {
            reward = new Reward();
            reward.setUserId(userId);
        }

        int total = reward.getTotalPoints() + points;
        reward.setTotalPoints(total);

        if (total < 100) {
            reward.setLevel("Beginner");
        } else if (total < 250) {
            reward.setLevel("Eco Starter");
        } else if (total < 500) {
            reward.setLevel("Eco Warrior");
        } else {
            reward.setLevel("Green Champion");
        }

        return rewardRepository.save(reward);
    }

    public Reward getReward(Long userId) {
        return rewardRepository.findByUserId(userId).orElse(null);
    }

    public java.util.List<Reward> getAllRewards() {
        return rewardRepository.findAll();
    }
}
