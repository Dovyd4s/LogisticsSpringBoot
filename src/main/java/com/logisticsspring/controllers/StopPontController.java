package com.logisticsspring.controllers;

import com.google.gson.Gson;
import com.logisticsspring.entities.StopPoint;
import com.logisticsspring.entities.Trip;
import com.logisticsspring.repository.StopPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Properties;

public class StopPontController {
    @Autowired
    private StopPointRepository stopPointRepository;

    public StopPontController(StopPointRepository stopPointRepository){
        this.stopPointRepository = stopPointRepository;
    }

    @GetMapping(value = "/allStops")
    public @ResponseBody Iterable<StopPoint> getAllStops(){
        return stopPointRepository.findAll();
    }
    @GetMapping(value = "/stop/{id}")
    public @ResponseBody StopPoint getStopPoint(@PathVariable(name = "id")int id){
        StopPoint stopPoint = stopPointRepository.findById(id).orElseThrow();
        return stopPoint;
    }
    @PostMapping(value = "addNewStop")
    public @ResponseBody String addNewStop (@RequestBody String info){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        StopPoint stopPoint = new StopPoint();
        stopPoint.setStopName(properties.getProperty("Name"));
        stopPoint.setStopDescription(properties.getProperty("Description"));
        stopPoint.setAddress(properties.getProperty("Address"));
        stopPoint.setCoordinates(properties.getProperty("Coordinates"));
        stopPoint.setTimeOfArrival(LocalDateTime.parse(properties.getProperty("TimeOfArrival")));
        stopPoint.setTimeOfDeparture(LocalDateTime.parse(properties.getProperty("TimeOfDeparture")));
        stopPoint = stopPointRepository.save(stopPoint);
        if(stopPointRepository.findById(stopPoint.getID()).isPresent()){
            return "Success";
        }else return "No Sakses.";
    }
    @PutMapping(value = "/updateStop/{id}")
    public @ResponseBody
    String updateTrip(@RequestBody String info, @PathVariable int id){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(info, Properties.class);
        StopPoint stopPoint = stopPointRepository.findById(id).orElse(null);
        stopPoint.setStopName(properties.getProperty("Name"));
        stopPoint.setStopDescription(properties.getProperty("Description"));
        stopPoint.setAddress(properties.getProperty("Address"));
        stopPoint.setCoordinates(properties.getProperty("Coordinates"));
        stopPoint.setTimeOfArrival(LocalDateTime.parse(properties.getProperty("TimeOfArrival")));
        stopPoint.setTimeOfDeparture(LocalDateTime.parse(properties.getProperty("TimeOfDeparture")));
        stopPointRepository.save(stopPoint);
        return "Success";
    }
    @DeleteMapping(value = "/deleteStop")
    public @ResponseBody String deleteStop(@PathVariable(name = "id")int id){
        stopPointRepository.deleteById(id);
        if(stopPointRepository.findById(id)==null){
            return "ok";
        }else return "not ok";
    }
}
