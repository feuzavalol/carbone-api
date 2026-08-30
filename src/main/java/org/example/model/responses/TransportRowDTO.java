package org.example.model.responses;

import java.util.UUID;

public record TransportRowDTO(
        UUID id,
        UUID transportId,
        String name,
        double distance,
        double co2Value) {}