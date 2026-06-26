package org.example.model.item;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="author_id", nullable = false)
    private UUID authorId;
    private String category;
    @Column(name="object_id", nullable = false)
    private UUID objectId;
    private double value;

    public FoodItem (UUID author_id, String category, UUID object_id, double value){
        this.authorId = author_id;
        this.category = category;
        this.objectId = object_id;
        this.value = value;
    }
}
