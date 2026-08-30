package org.example.repository.transport;

import org.example.model.transport.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.annotation.Nonnull;

import java.util.Optional;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {
    @Nonnull
    Optional<Route> findById(@Nonnull UUID uuid);
}