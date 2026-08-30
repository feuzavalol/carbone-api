package org.example.model.responses;

import java.util.UUID;

public record GoodRowDTO(UUID id, UUID goodId, String name, double quantity, String expected_unit, double co2Value) {}

