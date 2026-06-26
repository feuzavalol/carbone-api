package org.example.model.requests;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
public class CarbonRequest {
    @Getter
    private UUID foodId;
    @Getter
    private double quantity;
}