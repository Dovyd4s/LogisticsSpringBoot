package com.logisticsspring.controllers;

import com.google.gson.Gson;
import com.logisticsspring.entities.Driver;
import com.logisticsspring.entities.Trip;
import com.logisticsspring.repository.DriverRepository;
import com.logisticsspring.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

@RestController
public class TripController {
    @Autowired
    private TripRepository tripRepository;

    public TripController(TripRepository tripRepository){
        this.tripRepository = tripRepository;
    }

    @GetMapping(value = "/allTrips")
    public @ResponseBody Iterable<Trip> getAllTrips(){
        return tripRepository.findAll();
    }
    @GetMapping(value = "/getTrip/{id}")
    public @ResponseBody Trip getTripById (@PathVariable(name = "id")int id){
        Trip trip = tripRepository.findById(id).orElseThrow();
        return trip;
    }
    @PostMapping(value = "/addTrip")
    public @ResponseBody Trip addTrip (@RequestBody String info){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
            Trip trip = new Trip();
            trip.setDistance(Float.parseFloat(properties.getProperty("distance")));
            DriverRepository driverRepository = null;
            Driver driver = driverRepository.findById(Integer.valueOf(properties.getProperty("DriverID"))).orElse(null);
            trip.setAssignedDriver(driver);
            trip = tripRepository.save(trip);
        return tripRepository.findById(trip.getId()).orElse(null);
    }
    @DeleteMapping(value = "/deleteTrip/{id}")
    public @ResponseBody String deleteTrip (@PathVariable (name = "id")int id){
        tripRepository.deleteById(id);
        if(tripRepository.findById(id).isEmpty()){
            return "Success";
        }else return "No Ok";
    }
    @PutMapping(value = "/updateTrip/{id}")
    public @ResponseBody
    String updateTrip(@RequestBody String info, @PathVariable int id){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        Trip trip = tripRepository.findById(id).orElse(null);
        trip.setDistance(Float.parseFloat(properties.getProperty("distance")));
        //trip.setFuelConsumed(Float.parseFloat(properties.getProperty("fuelConsumed")));
        //trip.setAverageSpeed(Float.parseFloat(properties.getProperty("averageSpeed")));
        tripRepository.save(trip);
        return "Success";
    }

}
