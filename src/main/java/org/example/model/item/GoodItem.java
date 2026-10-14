package org.example.model.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
public class GoodItem implements Item {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="author_id", nullable = false)
    @Getter
    private UUID authorId;
    @Column(name="committee_id", nullable=false)
    @Getter
    private UUID committeeId;
    @Column(name="object_id", nullable = false)
    @Getter
    private UUID objectId;
    @Getter
    @Setter
    private double quantity;

    public GoodItem(){}

    public GoodItem(GoodItemDTO dto){
        this.authorId = UUID.fromString(dto.authorId());
        this.committeeId = UUID.fromString(dto.committeeId());
        this.quantity = dto.quantity();
        this.objectId = UUID.fromString(dto.objectId());
    }

    public static GoodItem createGoodItemWithId(GoodItemDTO dto) {
        GoodItem item = new GoodItem(dto);
        item.id = UUID.fromString(dto.id());
        return item;
    }
}
