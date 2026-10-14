package org.example.model.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
public class FoodItem implements Item {
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
    @Column(name="category_id", nullable=false)
    @Getter
    private UUID categoryId;
    @Getter
    @Column(name="object_id", nullable = false)
    private UUID objectId;
    @Getter
    @Setter
    private double quantity;

    public FoodItem(UUID authorId, UUID committeeId, UUID objectId, UUID categoryId, double quantity){
        this.authorId = authorId;
        this.committeeId = committeeId;
        this.objectId = objectId;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }

    public FoodItem(FoodItemDTO dto){
        this.objectId = UUID.fromString(dto.objectId());
        this.authorId = UUID.fromString(dto.authorId());
        this.committeeId = UUID.fromString(dto.committeeId());
        this.categoryId = UUID.fromString(dto.categoryId());
        this.quantity = dto.quantity();
    }

    public static FoodItem createFoodItemWithId(FoodItemDTO dto) {
        FoodItem item = new FoodItem(dto);
        item.id = UUID.fromString(dto.id());
        return item;
    }

    public FoodItem(){}
}
