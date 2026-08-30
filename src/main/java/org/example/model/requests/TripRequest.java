package org.example.model.requests;

import java.util.UUID;

public record TripRequest(UUID committeeId, String usage) {
}
