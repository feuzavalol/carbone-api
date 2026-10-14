package org.example.model.transport;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.model.item.TripDTO;

import java.util.UUID;

@Entity
public class Trip {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    @Column(name="committee_id", nullable = false)
    private UUID committeeId;
    @Getter
    @Column(name="author_id", nullable = false)
    private UUID authorId;
    @Getter
    @Column(name="usage_id", nullable = false)
    private UUID usageId;
    @Getter
    @Column(name="route_id")
    private UUID routeId;
    @Getter
    private String energy;
    @Getter
    private double consumption;
    @Getter
    @Column(name="quantity", nullable = false)
    private int quantity;
    @Getter
    private String comment;

    public Trip(TripDTO dto) {
        this.committeeId = UUID.fromString(dto.committeeId());
        this.authorId = UUID.fromString(dto.authorId());
        this.usageId = UUID.fromString(dto.usageId());
        this.consumption = dto.consumption();
        this.energy = dto.energy();
        this.quantity = dto.quantity();
        this.comment = dto.comment();
    }

    public Trip() {}

    public Trip(UUID id, UUID committeeId, UUID authorId, UUID usageId, String energy, int quantity, String comment) {
        this.id = id;
        this.committeeId = committeeId;
        this.authorId = authorId;
        this.usageId = usageId;
        this.energy = energy;
        this.quantity = quantity;
        this.comment = comment;
    }

    public static Trip createEmptyTrip(UUID id, UUID committeeId, UUID authorId, UUID usageId) {
        Trip trip = new Trip();
        trip.id = id;
        trip.committeeId = committeeId;
        trip.authorId = authorId;
        trip.usageId = usageId;
        trip.quantity = 0;
        trip.consumption = 0.0;
        return trip;
    }

    public static Trip createEmptyTrip(UUID committeeId, UUID authorId, UUID usageId) {
        Trip trip = new Trip();
        trip.committeeId = committeeId;
        trip.authorId = authorId;
        trip.usageId = usageId;
        trip.quantity = 0;
        trip.consumption = 0.0;
        return trip;
    }

    public static Trip createEmptyTrip(Trip trip){
        return createEmptyTrip(trip.id, trip.committeeId, trip.authorId, trip.usageId);
    }

    public static Trip createTripWithId(TripDTO dto) {
        Trip trip =  new Trip(dto);
        trip.id = UUID.fromString(dto.id());
        return trip;
    }

    @Override
    public String toString() {
        return "Trip item => {" +
                "id: "+id+
                ", committeeId: "+committeeId+
                ", authorId: "+authorId+
                ", usageId: '"+usageId+'\''+
                ", energy: '"+energy+'\''+
                ", consumption: "+consumption+
                ", quantity: "+quantity+
                ", comment: '"+comment+'\''+
                "}";
    }
}
