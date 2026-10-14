package org.example.repository;

import jakarta.annotation.Nonnull;
import org.example.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FoodRepo extends JpaRepository<Food, UUID> {
    Optional<Food> findByName(String name);
    @Nonnull
    Optional<Food> findById(@Nonnull UUID uuid);

    @Override
    boolean existsById(@Nonnull UUID uuid);
}