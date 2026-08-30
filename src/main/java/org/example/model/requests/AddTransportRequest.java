package org.example.model.requests;

import lombok.Data;
import org.example.model.transport.TransportItemDTO;

import java.util.List;

@Data
public class AddTransportRequest {
    private List<TransportItemDTO> items;
}
