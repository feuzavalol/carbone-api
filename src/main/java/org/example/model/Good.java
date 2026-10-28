package org.example.model;

import jakarta.annotation.Nonnull;
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
    @Convert(converter = UnitConverter.class)
    private Unit unit;
    @Getter
    @Column(name="expected_unit", nullable = false)
    @Convert(converter = ExceptedUnitConverter.class)
    private ExpectedUnit expectedUnit; // The unit the user has to follow
    @Getter
    @Enumerated(EnumType.STRING)
    private GoodCategory category;
    @Getter
    private String details;
    @Getter
    private String comments;
    @Getter
    private String source;

    public Good() {}

    public Good(UUID id, String name, double costPerUnit, Unit unit, ExpectedUnit expectedUnit, GoodCategory category, String details, String comments, String source) {
        this.id = id;
        this.name = name;
        this.costPerUnit = costPerUnit;
        this.unit = unit;
        this.expectedUnit = expectedUnit;
        this.details = details;
        this.comments = comments;
        this.source = source;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Good item => {" +
                "id: "+id+
                ", name: "+name+
                ", cost_per_unit: "+costPerUnit+
                ", unit: "+unit+
                ", expectedUnit: "+expectedUnit+
                ", category: "+category+
                ", details: "+details+
                ", comments: "+comments+
                ", source: "+source+
                "}";
    }
}
