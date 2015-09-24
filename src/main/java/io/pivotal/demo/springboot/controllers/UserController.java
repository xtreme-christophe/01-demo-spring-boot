package io.pivotal.demo.springboot.controllers;

import io.pivotal.demo.springboot.models.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/users")
    public User getUser() {

        User user = new User();
        user.setFirstname("John");
        user.setLastname("Smith");

        return user;
    }
}
