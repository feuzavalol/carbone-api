package org.example.repository;

import jakarta.annotation.Nonnull;
import org.example.model.Good;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GoodRepo extends JpaRepository<Good, UUID> {
    Optional<Good> findByName(String name);
    @Nonnull
    Optional<Good> findById(@Nonnull UUID uuid);

    @Override
    boolean existsById(UUID uuid);
}
