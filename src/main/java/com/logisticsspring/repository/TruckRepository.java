package com.logisticsspring.repository;

import com.logisticsspring.entities.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck,String> {
    Truck findByVIN(String vin);
    void deleteByVIN(String vin);
}
