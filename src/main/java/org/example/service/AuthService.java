package org.example.service;

import org.example.model.*;
import org.example.model.requests.LoginRequest;
import org.example.model.requests.RegisterRequest;
import org.example.model.responses.AuthResponse;
import org.example.repository.CommitteeRepo;
import org.example.repository.UserRepo;
import org.example.security.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CommitteeRepo committeeRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepo userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, CommitteeRepo committeeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.committeeRepository = committeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private AuthResponse getReponse(User user) {
        return new AuthResponse(true,"",jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getCommittee_id(), user.getId()));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email()).orElseThrow();
        return getReponse(user);
    }

    public AuthResponse saveUser(User user) {
        User savedUser;
        try {
            savedUser = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("Duplicate entry") && e.getMessage().contains("email")) {
                return new AuthResponse(false,"The email is already in use","");
            } else {
                System.out.println("Database constraint violation: " + e.getMessage());
                return new AuthResponse(false,"Database constraint violation: " + e.getMessage(),"");
            }
        }
        return getReponse(savedUser);
    }

    public AuthResponse register(RegisterRequest request) {
        CommitteeCategory category = request.committeeCategory();
        int number = request.committeeNumber();
        int year = request.year();
        Committee committee;
        if (number == 0){
            committee = committeeRepository.findByCategoryAndYear(
                    category,
                    year).orElseThrow(() -> new RuntimeException("No committee found with these informations: "+category+ " - "+year));
        }
        else {
            committee = committeeRepository.findByCategoryAndNumberAndYear(
                    category,
                    number,
                    year).orElseThrow(() -> new RuntimeException("No committee found with these informations: " + category + " - n°" + number + " - " + year));
        }
        User user = new User(
                request.username(),
                request.email(),
                passwordEncoder.encode(request.password()),
                request.role(),
                committee.getId()
        );
        return saveUser(user);
    }

}
