package org.example.model.responses;

import java.util.UUID;

public record PartyRowDTO(UUID id, UUID routeId, TripRowDTO busTrip, TripRowDTO carTrip) {
}
