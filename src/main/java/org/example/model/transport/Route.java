package org.example.model.transport;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    private String name;
    @Getter
    @Column(name="place1_id", nullable = false)
    private UUID placeId1;
    @Getter
    @Column(name="place2_id", nullable = false)
    private UUID placeId2;
    @Getter
    private double distance;
}
