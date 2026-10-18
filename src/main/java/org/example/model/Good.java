package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Table(name = "goods")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Getter
    private UUID id;
    @Getter
    private String name;
    @Getter
    @Column(name="cost_per_unit", nullable = false)
    private double costPerUnit;
    @Getter
    private String unit;
    @Getter
    @Column(name="expected_unit", nullable = false)
    private String expectedUnit; // The unit the user has to follow

    @Getter
    @Enumerated(EnumType.STRING)
    private GoodCategory category;
    @Getter
    private String details;
    @Getter
    private String comments;
    @Getter
    private String source;
}
