package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private double cost_per_unit;
    private String unit;
    private UUID food_group_id;
    private UUID sub_food_group_id;
    private String details;
    private UUID source_id;
}
