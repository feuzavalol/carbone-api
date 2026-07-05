package org.example.model.requests;

import lombok.Data;
import lombok.Getter;

@Data
public class TransportRequest {
    @Getter
    private String authorName;
    @Getter
    private String transportName;
    @Getter
    private double value;
}

