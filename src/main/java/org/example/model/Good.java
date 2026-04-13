package org.example.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private double cost_per_unit;
    private String unit;
    private UUID category_id;
    private String details;
    private String comments;
    private UUID source_id;
}
