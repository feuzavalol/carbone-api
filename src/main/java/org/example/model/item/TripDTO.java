package org.example.model.item;

import org.example.model.responses.TripRowDTO;

import java.util.UUID;

public record TripDTO(String id, String authorId, String committeeId, String usageId, int quantity, String energy, double consumption, String comment) implements ItemDTO {
    public TripRowDTO toDTO(){
        return new TripRowDTO(UUID.fromString(id),UUID.fromString(authorId),energy,consumption,quantity,comment);
    }
}
