package org.example.model.requests.food;

import lombok.Getter;

import java.util.UUID;

public class FoodItemRequest {
    @Getter
    private UUID committeeId;
    @Getter
    private UUID categoryId;

    public FoodItemRequest(UUID committeeId, UUID categoryId){
        this.committeeId = committeeId;
        this.categoryId = categoryId;
    }
}
