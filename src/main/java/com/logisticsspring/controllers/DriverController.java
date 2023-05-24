package com.logisticsspring.controllers;

import com.google.gson.Gson;
import com.logisticsspring.entities.Driver;
import com.logisticsspring.errors.UserNotFound;
import com.logisticsspring.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class DriverController {

    @Autowired
    private DriverRepository driverRepository;

    public DriverController(DriverRepository driverRepository){
        this.driverRepository = driverRepository;
    }

    @GetMapping (value = "/alldrivers")
    public @ResponseBody Iterable<Driver> getAllUsers(){
        return driverRepository.findAll();
    }

    @PostMapping (value = "/validatedriver")
    public @ResponseBody Driver validateDriver (@RequestBody String info){
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return driverRepository.findAllByLoginAndPassword(properties.getProperty("login"), properties.getProperty("password"));
    }

    @DeleteMapping(value = "/deleteDriver/{id}")
    public @ResponseBody String deleteDriver(@PathVariable (name = "id")int id){
        driverRepository.deleteById(id);
        if (driverRepository.findById(id) == null)return "OK";
        else return "NO SUCCESS!";
    }

    @GetMapping(value = "/driverbyID/{id}")
    EntityModel<Driver> getDriverById (@PathVariable(name = "id") int id){
        Driver driver = driverRepository.findById(id).orElseThrow(()->new UserNotFound(id));

        return EntityModel.of(driver,
                linkTo(methodOn(DriverController.class).getDriverById(id)).withSelfRel(),
                linkTo(methodOn(DriverController.class).getAllUsers()).withRel("dirvers"));
    }

    @PutMapping (value = "/updateDriver/{id}")
    public @ResponseBody
    String updateDriver(@RequestBody String driverInfoToUpdate, @PathVariable int id){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(driverInfoToUpdate, Properties.class);

        Driver driver = driverRepository.findById(id).orElseThrow(()->new UserNotFound(id));
        driver.setName(properties.getProperty("name"));
        driver.setLastName(properties.getProperty("lastName"));
        driver.setEmail(properties.getProperty("email"));
        driverRepository.save(driver);
        return "Success";
    }
}
