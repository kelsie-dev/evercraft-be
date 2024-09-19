package com.accenture.boot.camp.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoreGame {
    public CoreGame() {}

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/")

    public String getHelloWorld() { return "Hello, World!"; };

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/person")

    public String getHelloName(@RequestParam("name") String name){
        if(name.isBlank()){
            return "Hello, Person!";
        }
        return ("Hello, " + name + "!");
    }
}