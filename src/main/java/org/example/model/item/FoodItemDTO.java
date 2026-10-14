package org.example.model.item;

public record FoodItemDTO (String id, String authorId, String committeeId, String categoryId, String objectId, double quantity) implements ItemDTO{}
