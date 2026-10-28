package org.example.model;

import jakarta.persistence.*;
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
    private double costPerUnit;
    @Getter
    @Convert(converter = FoodUnitConverter.class)
    private FoodUnit unit;
    @Getter
    private String details;
    @Getter
    @Column(name="food_group", nullable = false)
    private String foodGroup;
    @Getter
    @Column(name="sub_food_group", nullable = false)
    private String subFoodGroup;
    @Getter
    private String source;

    public Food(UUID id, String name, double costPerUnit, FoodUnit unit) {
        this.id = id;
        this.name = name;
        this.costPerUnit = costPerUnit;
        this.unit = unit;
    }

    public Food(){}

    @Override
    public String toString() {
        return "Good item => {" +
                "id: "+id+
                ", name: "+name+
                ", cost_per_unit: "+costPerUnit+
                ", unit: "+unit+
                ", foodGroup: "+foodGroup+
                ", subFoodGroup: "+subFoodGroup+
                ", details: "+details+
                ", source: "+source+
                "}";
    }
}
