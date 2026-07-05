package org.example.repository;

import org.example.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.annotation.Nonnull;

import java.util.Optional;
import java.util.UUID;

public interface TransportRepo extends JpaRepository<Transport, UUID> {
    @Nonnull
    Optional<Transport> findById(@Nonnull UUID uuid);
    Optional<Transport> findByName(String name);
}