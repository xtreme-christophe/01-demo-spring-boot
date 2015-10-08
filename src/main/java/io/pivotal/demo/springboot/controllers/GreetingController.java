package io.pivotal.demo.springboot.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Value("${greetings:Hello}")
    private String greetings;

    @RequestMapping(value = "/greetings")
    public String greetings(@RequestParam(value = "name", defaultValue = "") String name) {
        return greetings + " " + name;
    }
}
