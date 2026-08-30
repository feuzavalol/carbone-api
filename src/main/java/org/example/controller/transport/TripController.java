package org.example.controller.transport;

import org.example.model.transport.Trip;
import org.example.model.requests.TripRequest;
import org.example.service.transport.RouteService;
import org.example.service.transport.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TripController {
    private final TripService service;
    private final RouteService routeService;

    public TripController(TripService tripService, RouteService routeService) {
        this.service = tripService;
        this.routeService = routeService;
    }

    @GetMapping("/trip")
    public ResponseEntity<List<Trip>> findAllTrip(
            @RequestParam UUID committeeId,
            @RequestParam String category
    ) {
        TripRequest request = new TripRequest(committeeId,category);
        List<Trip> result = service.getTripList(request);
        return ResponseEntity.ok(result);
    }

}
