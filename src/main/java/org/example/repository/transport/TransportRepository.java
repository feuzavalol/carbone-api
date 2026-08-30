package org.example.repository.transport;

import org.example.model.transport.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.annotation.Nonnull;

import java.util.Optional;
import java.util.UUID;

public interface TransportRepository extends JpaRepository<Transport, UUID> {
    @Nonnull
    Optional<Transport> findById(@Nonnull UUID uuid);
    Optional<Transport> findByName(String name);
}