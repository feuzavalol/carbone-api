package org.example.controller;


import org.example.model.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UsersController {
    private final UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }
    @GetMapping("/users")
    public List<User.UserDTO> findAllUser() {return service.toDTO(service.getUsers());}


    @GetMapping("/roles")
    public ResponseEntity<Map<String,List<String>>> findAllRoles(
            @RequestParam int year
    ){
        List<String> result = service.getRoles();
        return  ResponseEntity.ok(Map.of("success",result));
    }
}
