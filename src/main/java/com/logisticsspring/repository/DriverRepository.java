package com.logisticsspring.repository;

import com.logisticsspring.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
    Driver findAllByLoginAndPassword(String login, String password);
}
