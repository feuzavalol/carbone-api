package org.example.model.transport;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    @Column(name="route_id", nullable = false)
    private UUID routeId;
    @Getter
    @Column(name="committee_id", nullable = false)
    private UUID committeeId;
    @Getter
    @Column(name="author_id", nullable = false)
    private UUID authorId;
    @Getter
    private String usage;
    @Getter
    private String energy;
    @Getter
    private double consumption;
    @Getter
    @Column(name="number_round_trip", nullable = false)
    private int numberRoundTrip;
}
