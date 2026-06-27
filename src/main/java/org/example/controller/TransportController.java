package org.example.controller;

import org.example.model.Transport;
import org.example.model.responses.CarbonResponse;
import org.example.service.TransportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TransportController {
    private final TransportService service;

    public TransportController(TransportService service) {
        this.service = service;
    }

    @GetMapping("/transport")
    public List<Transport> findAllTransport() {return service.getTransportList();}

    @GetMapping("/transportCarbon")
    public ResponseEntity<CarbonResponse> findCarbonValue(
            @RequestParam UUID transportId,
            @RequestParam double distance
    ){
        return service.getCarbonValue(transportId,distance);
    }
}
