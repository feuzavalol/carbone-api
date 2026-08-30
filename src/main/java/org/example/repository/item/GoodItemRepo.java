package org.example.repository.item;

import jakarta.annotation.Nonnull;
import org.example.model.item.GoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GoodItemRepo extends JpaRepository<GoodItem, UUID> {
    @Nonnull
    Optional<GoodItem> findById(@Nonnull UUID id);

    Optional<List<GoodItem>> findByCommitteeId(UUID committeeId);
}
