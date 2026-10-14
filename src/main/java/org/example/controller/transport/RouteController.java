package org.example.controller.transport;

import org.example.model.responses.TripRowDTO;
import org.example.model.transport.Place;
import org.example.model.transport.Route;
import org.example.service.transport.PlaceService;
import org.example.service.transport.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class RouteController {
    private final RouteService service;
    private final PlaceService placeService;

    public RouteController(RouteService routeService, PlaceService placeService) {
        this.service = routeService;
        this.placeService = placeService;
    }

    @GetMapping("/routes")
    public ResponseEntity<Map<String,List<Route>>> getRoutes() {
        List<Route> result = service.findAll();
        Map<String,List<Route>> success = Map.of("success",result);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/places")
    public List<Place> findAllPlaces() {return placeService.getPlaceList();}

}
