package org.example.model.requests;

import lombok.Data;
import org.example.model.item.FoodItemDTO;
import org.example.model.responses.FoodRowDTO;

@Data
public class RemoveFoodRequest {
    private FoodItemDTO item;
}
