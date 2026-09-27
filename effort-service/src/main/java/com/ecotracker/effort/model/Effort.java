package com.ecotracker.effort.model;

import jakarta.persistence.*;

@Entity
@Table(name = "efforts")
public class Effort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String activityType;
    private double quantity;
    private String unit;
    private double co2Saved;
    private int ecoPoints;

    public Effort() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getCo2Saved() {
        return co2Saved;
    }

    public void setCo2Saved(double co2Saved) {
        this.co2Saved = co2Saved;
    }

    public int getEcoPoints() {
        return ecoPoints;
    }

    public void setEcoPoints(int ecoPoints) {
        this.ecoPoints = ecoPoints;
    }
}
