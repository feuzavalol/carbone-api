package org.example.controller;


import org.example.model.User;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UsersController {
    private final UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }
    @GetMapping("/users")
    public List<User> findAllEvents() {return service.getUsers();}
}
