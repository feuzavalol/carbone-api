package org.example.model.transport;

import jakarta.persistence.*;
import lombok.Getter;
import org.example.model.item.PartyDTO;
import org.example.model.responses.PartyRowDTO;
import org.example.model.responses.TripRowDTO;

import java.util.UUID;

@Entity
public class Party {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    @Column(name="committee_id",nullable = false)
    private UUID committeeId;
    @Getter
    @Column(name="author_id",nullable = false)
    private UUID authorId;
    @Getter
    @Column(name="route_id")
    private UUID routeId;
    @Getter
    @Column(name="bus_trip_id", nullable = false)
    private UUID busTripId;
    @Getter
    @Column(name="car_trip_id", nullable = false)
    private UUID carTripId;

    public Party () {}

    public Party(PartyDTO dto){
        this.busTripId = UUID.fromString(dto.busTrip().id());
        this.carTripId = UUID.fromString(dto.carTrip().id());
        this.committeeId = UUID.fromString(dto.committeeId());
        this.authorId = UUID.fromString(dto.authorId());
        this.routeId = UUID.fromString(dto.routeId());
    }

    public Party(UUID committeeId, UUID authorId, UUID routeId, UUID busTripId, UUID carTripId) {
        this.committeeId = committeeId;
        this.authorId = authorId;
        this.routeId = routeId;
        this.busTripId = busTripId;
        this.carTripId = carTripId;
    }

    public static Party createPartyWithId(PartyDTO dto){
        Party party = new Party(dto);
        party.id = UUID.fromString(dto.id());
        return party;
    }

    @Override
    public String toString() {
        return "Party item => {" +
                "id: "+id+
                ", committeeId: "+committeeId+
                ", authorId: "+authorId+
                ", routeId: "+routeId+
                ", busTripId: "+busTripId+
                ", carTripId: "+carTripId+
                "}";
    }
}
