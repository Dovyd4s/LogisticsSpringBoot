package com.logisticsspring.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;

    private LocalDate birthDate;
    private LocalDate employedSinceDate;

}
