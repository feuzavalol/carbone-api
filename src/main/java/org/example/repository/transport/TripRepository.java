package org.example.repository.transport;

import org.example.model.transport.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {
    @Nonnull
    Optional<Trip> findById(@Nonnull UUID uuid);
    Optional<List<Trip>> findByCommitteeId(UUID committeeId);

    Optional<List<Trip>> findByCommitteeIdAndUsage(UUID committeeId, String usage);
}