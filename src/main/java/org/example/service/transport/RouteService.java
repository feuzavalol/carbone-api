package org.example.service.transport;

import org.example.model.*;
import org.example.repository.*;
import org.example.repository.transport.PlaceRepository;
import org.example.repository.transport.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository repository;

    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

}
