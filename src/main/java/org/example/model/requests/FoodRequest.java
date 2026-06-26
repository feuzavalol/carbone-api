package org.example.model.requests;

import lombok.Data;
import lombok.Getter;

@Data
public class FoodRequest {
    @Getter
    private String authorName;
    @Getter
    private String foodName;
    @Getter
    private double value;
}

