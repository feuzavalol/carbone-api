package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
public class Food {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    private String name;
    @Getter
    private double cost_per_unit;
    @Getter
    private String unit;
    @Getter
    private String details;
    @Getter
    private String food_group;
    @Getter
    private String sub_food_group;
    @Getter
    private UUID source;
}
