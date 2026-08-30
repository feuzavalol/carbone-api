package org.example.model.requests;

import lombok.Data;
import org.example.model.item.GoodItemDTO;

import java.util.List;

@Data
public class AddGoodRequest {
    private List<GoodItemDTO> items;
}
