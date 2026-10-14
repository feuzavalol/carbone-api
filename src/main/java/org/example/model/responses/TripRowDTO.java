package org.example.model.responses;

import java.util.UUID;

public record TripRowDTO(UUID id, UUID usageId, String energy, double consumption, int quantity, String comment) {
}
