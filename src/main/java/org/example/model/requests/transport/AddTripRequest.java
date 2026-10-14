package org.example.model.requests.transport;

import org.example.model.item.TripDTO;

import java.util.List;

public record AddTripRequest(List<TripDTO> items) {
}
