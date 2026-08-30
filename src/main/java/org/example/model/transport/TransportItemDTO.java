package org.example.model.transport;

import org.example.model.item.ItemDTO;

public record TransportItemDTO(String id, String objectId, String authorId, String committeeId, double distance) implements ItemDTO {
}
