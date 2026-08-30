package org.example.model.requests;

import lombok.Data;
import org.example.model.item.GoodItemDTO;

@Data
public class RemoveGoodRequest {
    private GoodItemDTO item;
}
