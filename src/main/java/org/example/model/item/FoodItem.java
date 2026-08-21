package org.example.model.item;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class FoodItem {
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
    @Getter
    private String category;
    @Getter
    @Column(name="object_id", nullable = false)
    private UUID objectId;
    @Getter
    private double quantity;

    public FoodItem(UUID authorId, UUID committeeId, UUID objectId, String category, double quantity){
        this.authorId = authorId;
        this.committeeId = committeeId;
        this.objectId = objectId;
        this.category = category;
        this.quantity = quantity;
    }

    public FoodItem(FoodItemDTO dto){
        this.objectId = UUID.fromString(dto.objectId());
        this.authorId = UUID.fromString(dto.authorId());
        this.committeeId = UUID.fromString(dto.committeeId());
        this.category = dto.category();
        this.quantity = dto.quantity();
    }

    public static FoodItem createFoodItemWithId(FoodItemDTO dto) {
        FoodItem item = new FoodItem(dto);
        item.id = UUID.fromString(dto.id());
        return item;
    }

    public FoodItem(){}
}
