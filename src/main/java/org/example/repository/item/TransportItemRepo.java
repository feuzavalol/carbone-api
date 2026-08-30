package org.example.repository.item;

import org.example.model.transport.TransportItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransportItemRepo extends JpaRepository<TransportItem, UUID> {
    Optional<List<TransportItem>> findByAuthorId(UUID authorId);
    Optional<List<TransportItem>> findByCommitteeId(UUID committeeId);

    boolean existsByObjectId(UUID objectId);

    boolean existsByObjectIdAndCommitteeId(UUID objectId, UUID committeeId);
}