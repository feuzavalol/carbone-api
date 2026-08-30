package org.example.repository.transport;

import org.example.model.transport.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository<Place, UUID> {
    @Nonnull
    Optional<Place> findById(@Nonnull UUID uuid);
    List<Place> findAll();
}