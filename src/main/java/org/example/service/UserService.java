package org.example.service;

import org.example.model.*;
import org.example.model.requests.LoginRequest;
import org.example.repository.CommitteeRepo;
import org.example.repository.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final CommitteeRepo committeeRepository;
    private final UserRepo userRepository;

    // Spring injects the repositories automatically via the constructor
    public UserService(CommitteeRepo committeeRepository, UserRepo userRepository) {
        this.committeeRepository = committeeRepository;
        this.userRepository = userRepository;
    }

    public User.UserDTO toDTO(User user) {
        return new User.UserDTO(user.getName(), user.getRole(), user.isActive());
    }

    public List<User.UserDTO> toDTO(List<User> users){
        List<User.UserDTO> usersDTO = new ArrayList<>();
        users.forEach((user) -> usersDTO.add(toDTO(user)));
        return usersDTO;
    }

    public User createUser(String name, String email, String password, Role role, Category category, int number) {
        Committee committee = committeeRepository
                .findByCategoryAndNumber(category, number)
                .orElseThrow(() -> new RuntimeException("Committee not found: " + category + " - " + number));

        return new User(name, email, password, role, committee.getId());
    }

    public User createUser(LoginRequest request) {
        Committee committee = committeeRepository
                .findByCategoryAndNumber(request.getCommitteeCategory(), request.getCommitteeNumber())
                .orElseThrow(() -> new RuntimeException("Committee not found: " + request.getCommitteeCategory() + " - " + request.getCommitteeNumber()));

        return new User(request.getUsername(), request.getEmail(), request.getPassword(), Role.VIS, committee.getId());
    }

    @Transactional
    public void createAndSaveUser(String name, String email, String password, Role role, Category category, int number) {
        User user = createUser(name,email,password,role,category,number);
        userRepository.save(user);
    }

    public List<User> getUsers(){return userRepository.findAll();}
}
