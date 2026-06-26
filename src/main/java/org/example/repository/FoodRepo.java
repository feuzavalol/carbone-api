package org.example.repository;

import org.example.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FoodRepo extends JpaRepository<Food, UUID> {
    Optional<Food> findByName(String name);
    Optional<Food> findById(UUID uuid);
}