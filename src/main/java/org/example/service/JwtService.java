package org.example.service;

import org.example.model.Category;
import org.example.model.Committee;
import org.example.model.Role;
import org.example.model.User;
import org.example.model.requests.LoginRequest;
import org.example.repository.CommitteeRepo;
import org.example.repository.UserRepo;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@Service
public class JwtService {

    private final PasswordEncoder passwordEncoder;
    private final CommitteeRepo committeeRepo;
    private final UserRepo userRepo;
    private final JwtEncoder jwtEncoder;

    public JwtService(PasswordEncoder passwordEncoder, UserRepo userRepo, CommitteeRepo committeeRepo, JwtEncoder jwtEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.committeeRepo = committeeRepo;
        this.userRepo = userRepo;
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String username, Role role, UUID userId) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("user-service")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(username)
                .claim("userId", userId)
                .claim("role", role)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public Map<String, String> register(LoginRequest request) {
        Category category = request.getCommitteeCategory();
        int number = request.getCommitteeNumber();
        Committee committee = committeeRepo
                .findByCategoryAndNumber(category, number)
                .orElseThrow(() -> new RuntimeException("Committee not found: " + category + " - " + number));
        // Créer le nouvel utilisateur
        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()), request.getRole(), committee.getId());

        User savedUser = userRepo.save(user);

        // Générer le token JWT
        String token = generateToken(request.getUsername(), Role.VIS, savedUser.getId());

        return Map.of("token", token);
    }
}
