package com.logisticsspring.entities;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Manager extends User {
    private boolean isAdmin;
    private float bonusAmountPerKmEUR;
    private int minDistanceToGetBonus;
}
