package org.example.model.responses;

import java.util.UUID;

public record FoodRowDTO(UUID id, UUID foodId, String name, UUID categoryId, double quantity, double co2Value) {}
