package org.example.model.requests.food;

import lombok.Data;
import org.example.model.item.FoodItemDTO;

@Data
public class RemoveFoodItemRequest {
    private FoodItemDTO item;
}
