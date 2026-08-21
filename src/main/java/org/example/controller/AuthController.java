package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Role;
import org.example.model.User;
import org.example.model.requests.LoginRequest;
import org.example.model.requests.RegisterRequest;
import org.example.model.responses.AuthResponse;
import org.example.repository.UserRepo;
import org.example.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        // Delegates to CustomUserDetailsService + password check
        System.out.println("login attempt");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email()).orElseThrow();
        return new AuthResponse(jwtUtil.generateToken(user.getEmail(), user.getRole()));
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        User user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.VIS, // or whatever default
                UUID.fromString("833ee25c-3d98-11f1-825c-a651a351d1ac") // Random committee id, will be changed later
        );
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user.getEmail(), user.getRole()));
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testAuth(){
        Map<String, String> success = Map.of("success","Vous êtes correctement authentifié.e.s");
        return ResponseEntity.ok(success);
    }
}