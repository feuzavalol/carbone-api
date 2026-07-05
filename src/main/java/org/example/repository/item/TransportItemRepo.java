package org.example.repository.item;

import org.example.model.item.TransportItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransportItemRepo extends JpaRepository<TransportItem, UUID> {
    Optional<List<TransportItem>> findByAuthorId(UUID authorId);
}