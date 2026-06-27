package org.example.repository;

import org.example.model.Food;
import org.example.model.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransportRepo extends JpaRepository<Transport, UUID> {
    Optional<Transport> findByName(String name);
    Optional<Transport> findById(UUID uuid);
}