package org.example.repository.transport;

import jakarta.annotation.Nonnull;
import org.example.model.transport.Party;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PartyRepository extends JpaRepository<Party, UUID> {
    @Nonnull
    Optional<Party> findById(@Nonnull UUID uuid);

    Optional<Party> findByCommitteeId(UUID committeeId);
}
