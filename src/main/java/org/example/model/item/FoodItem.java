package org.example.model.item;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class FoodItem {
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
    private double quantity;

    public FoodItem(UUID authorId, String category, UUID foodId, double quantity) {
        this.authorId = authorId;
        this.category = category;
        this.objectId = foodId;
        this.quantity = quantity;
    }
}
