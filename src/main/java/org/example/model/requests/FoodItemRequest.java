package org.example.model.requests;

import lombok.Getter;

import java.util.UUID;

public class FoodItemRequest {
    @Getter
    private UUID committeeId;
    @Getter
    private String category;

    public FoodItemRequest(UUID committeeId,String category){
        this.committeeId = committeeId;
        this.category = category;
    }
}
