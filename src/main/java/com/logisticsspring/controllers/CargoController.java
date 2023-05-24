package com.logisticsspring.controllers;

import com.google.gson.Gson;
import com.logisticsspring.entities.Cargo;
import com.logisticsspring.repository.CargoRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Properties;

@RestController
public class CargoController {
    @Autowired
    private CargoRepositary cargoRepositary;
    public CargoController(CargoRepositary cargoRepositary){
        this.cargoRepositary = cargoRepositary;
    }

    @GetMapping(value = "/Cargo/{id}")
    public @ResponseBody Cargo getCarboById (@PathVariable(name="id")int id){
        Cargo cargo = cargoRepositary.getReferenceById(id);
        return cargo;
    }
    @DeleteMapping(value = "deleteCargo/{id}")
    public @ResponseBody String deleteCargo (@PathVariable(name="id")int id){
        cargoRepositary.deleteById(id);
        if(cargoRepositary.existsById(id)){
            return "fail";
        }else return "ok";
    }
    @PostMapping(value = "/addCargo")
    public @ResponseBody Cargo addCargo(@RequestBody String info){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        Cargo cargo = new Cargo();
        cargo.setValueOfCargoEUR(Float.parseFloat(properties.getProperty("value")));
        cargo.setTotalCargoWeightTonnes(Float.parseFloat(properties.getProperty("weight")));
        cargo.setReadyForPickUpDate(LocalDate.parse(properties.getProperty("pickupDate")));
        cargo.setMustBeDeliveredUntilDate(LocalDate.parse(properties.getProperty("mustBeDeliveredDate")));
        cargo = cargoRepositary.save(cargo);
        return cargo;
    }
}
