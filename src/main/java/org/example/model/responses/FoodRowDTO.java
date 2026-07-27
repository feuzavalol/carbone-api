package org.example.model.responses;

import java.util.UUID;

public record FoodRowDTO(UUID id, UUID foodId, String name, String category, double quantity, double co2Value) {}
