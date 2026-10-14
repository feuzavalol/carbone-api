package org.example.model.item;

public record PartyDTO(String id, String committeeId, String authorId, TripDTO busTrip, TripDTO carTrip, String routeId) {
}
