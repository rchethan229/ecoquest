package com.ecotracker.effort.service;

import com.ecotracker.effort.client.RewardClient;
import com.ecotracker.effort.model.Effort;
import com.ecotracker.effort.repository.EffortRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EffortService {

    @Autowired
    private EffortRepository effortRepository;

    @Autowired
    private RewardClient rewardClient;

    public Effort addEffort(Effort effort, Long userId) {

        // Never trust userId sent by the browser. The authenticated JWT decides the owner.
        effort.setUserId(userId);

        double co2 = 0;
        int points = 0;

        String activity = effort.getActivityType();

        if ("Cycling".equalsIgnoreCase(activity)) {
            co2 = effort.getQuantity() * 0.5;
            points = (int) (effort.getQuantity() * 5);
        } else if ("Public Transport".equalsIgnoreCase(activity)) {
            co2 = effort.getQuantity() * 0.2;
            points = (int) (effort.getQuantity() * 3);
        } else if ("Reusable Bottle".equalsIgnoreCase(activity)) {
            co2 = effort.getQuantity() * 0.1;
            points = (int) (effort.getQuantity() * 2);
        } else if ("Energy Saving".equalsIgnoreCase(activity)) {
            co2 = effort.getQuantity() * 0.4;
            points = (int) (effort.getQuantity() * 4);
        } else if ("Tree Planting".equalsIgnoreCase(activity)) {
            co2 = effort.getQuantity() * 20;
            points = (int) (effort.getQuantity() * 10);
        }

        effort.setCo2Saved(co2);
        effort.setEcoPoints(points);

        Effort savedEffort = effortRepository.save(effort);

        // Service-to-service communication using OpenFeign.
        rewardClient.addPoints(
                effort.getUserId(),
                effort.getEcoPoints());

        return savedEffort;
    }

    public List<Effort> getEffortsByUser(Long userId) {
        return effortRepository.findByUserId(userId);
    }

    public List<Effort> getAllEfforts() {
        return effortRepository.findAll();
    }
}
