package org.example;

import org.example.model.Category;
import org.example.model.Role;
import org.example.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // This runs once on startup — good for quick tests
    @Bean
    public ApplicationRunner run(UserService userService) {
        return args -> {
            // userService.createAndSaveUser("Jean", "jean@gmail.com","mdpsolide", Role.LIS, Category.BDS, 1);
        };
    }
}