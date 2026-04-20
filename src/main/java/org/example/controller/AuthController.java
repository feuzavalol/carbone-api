package org.example.controller;

import org.example.repository.UserRepo;
import org.example.service.JwtService;
import org.example.model.User;
import org.example.model.requests.LoginRequest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepo userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByName(request.getUsername());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Identifiants invalides"));
        }
        String token = jwtService.generateToken(user.getName(), user.getRole(), user.getId());
        Map<String, String> success = Map.of("token", token);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody LoginRequest request) {
        Map<String, String> success = jwtService.register(request);
        return ResponseEntity.ok(success);
    }
}

