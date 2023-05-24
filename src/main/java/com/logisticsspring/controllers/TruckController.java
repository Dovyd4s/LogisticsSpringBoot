package com.logisticsspring.controllers;

import com.google.gson.Gson;
import com.logisticsspring.entities.Truck;
import com.logisticsspring.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Properties;

@RestController
public class TruckController {
    @Autowired
    private TruckRepository truckRepository;

    public TruckController(TruckRepository truckRepository){
        this.truckRepository = truckRepository;
    }

    @GetMapping(value = "/allTrucks")
    public @ResponseBody Iterable<Truck> getAllTrucks(){
        return truckRepository.findAll();
    }

    @PostMapping(value = "/addTruck")
    public @ResponseBody Truck addTruck (@RequestBody String info){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        if(truckRepository.findByVIN(properties.getProperty("VIN"))==null){
            Truck truck = new Truck();
            truck.setMake(properties.getProperty("Make"));
            truck.setModel(properties.getProperty("Model"));
            truck.setVIN(properties.getProperty("VIN"));
            truck.setPlateNumber(properties.getProperty("PlateNumber"));
            truck.setAverageFuelConsumption(Float.parseFloat(properties.getProperty("averageFuelConsumption","0")));
            truck.setMaxCapacityTonnes(Float.parseFloat(properties.getProperty("maxCapacity","0.0")));
            truck.setWeightTonnes(Float.parseFloat(properties.getProperty("weight","0")));
            truck.setTankSizeLiters(Float.parseFloat(properties.getProperty("tankSize","0")));
            //truck.setManufacturedDate(LocalDate.parse(properties.getProperty("manufactureDate",null)));
            //truck.setLastTechnicalStateCheckDate(LocalDate.parse(properties.getProperty("lastTechnicalCheck",null)));
            truckRepository.save(truck);
        }
        return truckRepository.findByVIN(properties.getProperty("VIN"));
    }
    @PutMapping (value = "/updateTruck/{vin}")
    public @ResponseBody
    String updateTruck(@RequestBody String truckInfo, @PathVariable String vin){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(truckInfo, Properties.class);

        Truck truck = truckRepository.findByVIN(vin);
        truck.setTankSizeLiters(Float.parseFloat(properties.getProperty("tankSize")));
        truck.setWeightTonnes(Float.parseFloat(properties.getProperty("weight")));
        truck.setAverageFuelConsumption(Float.parseFloat(properties.getProperty("averageConsuption")));
        truckRepository.save(truck);
        return "Success";
    }

    @DeleteMapping(value = "/deleteTruck/{vin}")
    public @ResponseBody String deleteTruck (@PathVariable (name = "vin")String vin){
        truckRepository.deleteByVIN(vin);
        if(truckRepository.findByVIN(vin) == null){
            return "OK";
        }else{
            return "Not OK";
        }
    }
}
