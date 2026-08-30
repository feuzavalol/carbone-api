package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Committee;
import org.example.model.CommitteeCategory;
import org.example.model.Role;
import org.example.model.User;
import org.example.model.requests.LoginRequest;
import org.example.model.requests.RegisterRequest;
import org.example.model.responses.AuthResponse;
import org.example.repository.CommitteeRepo;
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
    private final CommitteeRepo committeeRepository;
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
        return new AuthResponse(jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getCommittee_id(), user.getId()));
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
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user.getEmail(), user.getRole(), null, user.getId()));
    }

    @PostMapping("registerAdmin")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        CommitteeCategory category = request.committeeCategory();
        int number = request.committeeNumber();
        int year = request.year();
        Committee committee = committeeRepository.findByCategoryAndNumberAndYear(
                category,
                number,
                year).orElseThrow(() -> new RuntimeException("No committee found with these informations: "+category+" - n°"+number+" - "+year));
        User user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                Role.LIS, // TODO: other users are created by a separate script
                committee.getId()
        );
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getCommittee_id(), user.getId()));
    }


    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testAuth(){
        Map<String, String> success = Map.of("success","Vous êtes correctement authentifié.e.s");
        return ResponseEntity.ok(success);
    }
}