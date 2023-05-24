package com.logisticsspring.entities;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    private StopPoint startPoint;
    @OneToOne
    private StopPoint destinationPoint;
    @OneToMany
    private List<StopPoint> allRestPoints;
    @ManyToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Driver assignedDriver;
    @ManyToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Manager assignedManager;
    private float distance;
    private float fuelConsumed;
    private float averageSpeed;
    @OneToOne
    private Cargo cargo;

}
