package org.example.model.item;

public record GoodItemDTO (String id, String authorId, String committeeId, String objectId, double quantity) implements ItemDTO {}

