package com.logisticsspring.repository;

import com.logisticsspring.entities.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepositary extends JpaRepository<Cargo, Integer> {
}
