package io.pivotal.demo.springboot.controllers;

import io.pivotal.demo.springboot.models.User;
import io.pivotal.demo.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/users",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> getUser(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    /**
     * GET  /users/:id -> get a specific user.
     */
    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<User> getUser(@PathVariable Long id) {

        User user = userRepository.findOne(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * POST  /users -> Create a new user.
     */
    @RequestMapping(value = "/users",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> create(@RequestBody User user) throws URISyntaxException {
        if (user.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new user cannot already have an ID").body(null);
        }

        User newUser = new User();
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setEmail(user.getEmail());
        userRepository.save(newUser);

        return ResponseEntity.created(new URI("/users/" + user.getId())).body(newUser);
    }

    /**
     * PUT  /users -> Updates an existing user.
     */
    @RequestMapping(value = "/users",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@RequestBody User user) throws URISyntaxException {
        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    /**
     * DELETE  /users/:id -> delete the "id" user.
     */
    @RequestMapping(value = "/users/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        try {
            userRepository.delete(id);
        } catch (Exception e) {
        }

        return ResponseEntity.ok().build();
    }
}
