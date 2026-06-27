package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;
    @Getter
    private String name;
    @Getter
    private String type;
    @Getter
    private double cost_per_unit;
    @Getter
    private String unit;
    @Getter
    private String details;
    @Getter
    private String comments;
    @Getter
    private String quantity_unit;
    @Getter
    private String source;
}
