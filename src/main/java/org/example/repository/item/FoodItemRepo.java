package org.example.repository.item;

import jakarta.annotation.Nonnull;
import org.example.model.item.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodItemRepo extends JpaRepository<FoodItem, UUID> {
    Optional<List<FoodItem>> findByCommitteeIdAndCategory(UUID committeeId, String category);
    @Nonnull
    Optional<FoodItem> findById(@Nonnull UUID id);
}