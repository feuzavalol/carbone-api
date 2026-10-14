package org.example.repository.transport;

import jakarta.annotation.Nonnull;
import org.example.model.transport.UsageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsageRepository extends JpaRepository<UsageType, UUID> {
    @Nonnull
    Optional<UsageType> findById(@Nonnull UUID uuid);

    Optional<UsageType> findByName(String name);
}
