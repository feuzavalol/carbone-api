package org.example.repository.item;

import jakarta.annotation.Nonnull;
import org.example.model.item.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodItemRepo extends JpaRepository<FoodItem, UUID> {
    @Nonnull
    Optional<FoodItem> findById(@Nonnull UUID id);

    Optional<List<FoodItem>> findByCommitteeIdAndCategoryId(UUID committeeId, UUID categoryId);

    Optional<List<FoodItem>> findByCommitteeId(UUID committeeId);
}