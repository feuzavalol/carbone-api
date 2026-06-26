package org.example.model.requests;

import lombok.Data;

import java.util.List;

@Data
public class AddFoodRequest {
    private List<FoodRequest> foodRequestList;
}
