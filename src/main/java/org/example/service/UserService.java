package org.example.service;

import org.example.model.*;
import org.example.model.requests.RegisterRequest;
import org.example.repository.CommitteeRepo;
import org.example.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final CommitteeRepo committeeRepository;
    private final UserRepo userRepository;

    // Spring injects the repositories automatically via the constructor
    public UserService(CommitteeRepo committeeRepository, UserRepo userRepository) {
        this.committeeRepository = committeeRepository;
        this.userRepository = userRepository;
    }

    public UUID parseId(String rawId) {
        return (rawId == null || rawId.isBlank()) ? null : UUID.fromString(rawId);
    }


    public User.UserDTO toDTO(User user) {
        return new User.UserDTO(user.getName(), user.getRole(), user.isActive());
    }

    public List<User.UserDTO> toDTO(List<User> users){
        List<User.UserDTO> usersDTO = new ArrayList<>();
        users.forEach((user) -> usersDTO.add(toDTO(user)));
        return usersDTO;
    }

    public User createUser(String name, String email, String password, Role role, CommitteeCategory committeeCategory, int number, int year) {
        Committee committee = committeeRepository
                .findByCategoryAndNumberAndYear(committeeCategory, number, year)
                .orElseThrow(() -> new RuntimeException("Committee not found: " + committeeCategory + " - " + number));

        return new User(name, email, password, role, committee.getId());
    }

    public User createUser(RegisterRequest request) {
        Committee committee = committeeRepository
                .findByCategoryAndNumberAndYear(request.committeeCategory(), request.committeeNumber(),request.year())
                .orElseThrow(() -> new RuntimeException("Committee not found: " + request.committeeCategory() + " - " + request.committeeCategory()));

        return new User(request.username(), request.email(), request.password(), Role.VIS, committee.getId());
    }

    @Transactional
    public void createAndSaveUser(String name, String email, String password, Role role, CommitteeCategory committeeCategory, int number, int year) {
        User user = createUser(name,email,password,role, committeeCategory,number,year);
        userRepository.save(user);
    }

    public List<User> getUsers(){return userRepository.findAll();}
}
