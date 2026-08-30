package org.example.controller.transport;

import org.example.model.transport.Place;
import org.example.service.transport.PlaceService;
import org.example.service.transport.RouteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class RouteController {
    private final RouteService service;
    private final PlaceService placeService;

    public RouteController(RouteService routeService, PlaceService placeService) {
        this.service = routeService;
        this.placeService = placeService;
    }

    @GetMapping("/places")
    public List<Place> findAllPlaces() {return placeService.getPlaceList();}

}
