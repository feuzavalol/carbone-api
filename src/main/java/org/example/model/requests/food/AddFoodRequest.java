package org.example.model.requests.food;

import lombok.Data;
import org.example.model.item.FoodItemDTO;

import java.util.List;

@Data
public class AddFoodRequest {
    private List<FoodItemDTO> items;
}
