package com.logisticsspring.controllers;

import com.google.gson.Gson;
import com.logisticsspring.entities.Manager;
import com.logisticsspring.errors.UserNotFound;
import com.logisticsspring.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ManagerController {

    @Autowired
    private ManagerRepository managerRepository;

    public ManagerController(ManagerRepository managerRepository){
        this.managerRepository = managerRepository;
    }

    @GetMapping (value = "/allmanagers")
    public @ResponseBody Iterable<Manager> getAllUsers(){
        return managerRepository.findAll();
    }

    @PostMapping (value = "/validatemanager")
    public @ResponseBody Manager validateManager (@RequestBody String info){
        Gson parser = new Gson();
        Properties properties = parser.fromJson(info, Properties.class);
        return managerRepository.findAllByLoginAndPassword(properties.getProperty("login"), properties.getProperty("password"));
    }

    @DeleteMapping(value = "/deleteManager/{id}")
    public @ResponseBody String deleteManager(@PathVariable (name = "id")int id){
        managerRepository.deleteById(id);
        if (managerRepository.findById(id) == null)return "OK";
        else return "NO SUCCESS!";
    }

    @GetMapping(value = "/managerbyID/{id}")
    EntityModel<Manager> getManagerById (@PathVariable(name = "id") int id){
        Manager manager = managerRepository.findById(id).orElseThrow(()->new UserNotFound(id));

        return EntityModel.of(manager,
                linkTo(methodOn(ManagerController.class).getManagerById(id)).withSelfRel(),
                linkTo(methodOn(ManagerController.class).getAllUsers()).withRel("dirvers"));
    }

    @PutMapping (value = "/updateManager/{id}")
    public @ResponseBody
    String updateManager(@RequestBody String managerInfoToUpdate, @PathVariable int id){
        Gson gson = new Gson();
        Properties properties = gson.fromJson(managerInfoToUpdate, Properties.class);

        Manager manager = managerRepository.findById(id).orElseThrow(()->new UserNotFound(id));
        manager.setName(properties.getProperty("name"));
        manager.setLastName(properties.getProperty("lastName"));
        manager.setEmail(properties.getProperty("email"));
        managerRepository.save(manager);
        return "Success";
    }
}

