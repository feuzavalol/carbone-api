package org.example;

import org.example.model.CommitteeCategory;
import org.example.model.Committee;
import org.example.repository.CommitteeRepo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // This runs once on startup — good for quick tests
    @Bean
    public ApplicationRunner run(CommitteeRepo committeeRepo) {
        return args -> {
            Optional<Committee> committee = committeeRepo.findByCategoryAndNumberAndYear(CommitteeCategory.BDE,1,2025);
            // userService.createAndSaveUser("Jean", "jean@gmail.com","mdpsolide", Role.LIS, Category.BDS, 1);
        };
    }
}