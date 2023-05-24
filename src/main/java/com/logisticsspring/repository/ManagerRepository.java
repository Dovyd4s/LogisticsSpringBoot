package com.logisticsspring.repository;

import com.logisticsspring.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findAllByLoginAndPassword(String login, String password);
}
