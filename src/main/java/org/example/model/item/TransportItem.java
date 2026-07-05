package org.example.model.item;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class TransportItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="author_id", nullable = false)
    private UUID authorId;
    private String category;
    @Getter
    @Column(name="object_id", nullable = false)
    private UUID objectId;
    @Getter
    private double distance;

    public TransportItem(UUID authorId, String category, UUID transportId, double distance) {
        this.authorId = authorId;
        this.category = category;
        this.objectId = transportId;
        this.distance = distance;
    }
}
