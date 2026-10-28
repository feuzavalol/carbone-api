package org.example.model.requests.food;

import lombok.Data;
import org.example.model.Food;

@Data
public class RemoveFoodRequest {
    private Food food;
}