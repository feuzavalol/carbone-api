package org.example.model.requests.good;

import lombok.Data;
import org.example.model.Good;

@Data
public class ModifyGoodRequest {
    private Good good;
}
