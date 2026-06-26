package org.example.repository;

import org.example.model.Category;
import org.example.model.Committee;
import org.example.model.item.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodItemRepo extends JpaRepository<FoodItem, UUID> {
    Optional<List<FoodItem>> findByAuthorId(UUID authorId);
}