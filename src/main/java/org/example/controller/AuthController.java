package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Role;
import org.example.model.User;
import org.example.model.requests.LoginRequest;
import org.example.model.requests.RegisterRequest;
import org.example.model.responses.AuthResponse;
import org.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        // Delegates to CustomUserDetailsService + password check
        System.out.println("login attempt");

        return authService.login(request);
    }

    @PostMapping("/register")
    public AuthResponse baseRegister(@RequestBody RegisterRequest request) {
        User user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.VIS,
                null
        );
        return authService.saveUser(user);
    }

    @PostMapping("registerAdmin")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }


    @GetMapping("/testAuth")
    public ResponseEntity<Map<String, String>> testAuth(){
        Map<String, String> success = Map.of("success","Vous êtes correctement authentifié.e.s");
        return ResponseEntity.ok(success);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testAPI(){
        Map<String, String> success = Map.of("success","L'API est en ligne !");
        return ResponseEntity.ok(success);
    }
}