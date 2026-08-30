package org.example.model.transport;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class TransportItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;
    @Getter
    @Column(name="author_id", nullable = false)
    private UUID authorId;
    @Getter
    @Column(name="committee_id", nullable = false)
    private UUID committeeId;
    @Getter
    @Column(name="object_id", nullable = false)
    private UUID objectId;
    @Getter
    private double distance;

    public TransportItem(UUID authorId, UUID committeeId, UUID transportId, double distance) {
        this.authorId = authorId;
        this.committeeId = committeeId;
        this.objectId = transportId;
        this.distance = distance;
    }

    public TransportItem() {}

    public TransportItem(TransportItemDTO dto){
        this.objectId = UUID.fromString(dto.objectId());
        this.authorId = UUID.fromString(dto.authorId());
        this.committeeId = UUID.fromString(dto.committeeId());
        this.distance = dto.distance();
    }

    public static TransportItem createTransportItemWithId(TransportItemDTO dto) {
        TransportItem item = new TransportItem(dto);
        item.id = UUID.fromString(dto.id());
        return item;
    }
}
