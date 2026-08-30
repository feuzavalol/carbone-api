package org.example.service.transport;

import org.example.model.transport.Place;
import org.example.repository.transport.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {
    private final PlaceRepository repository;

    public PlaceService(PlaceRepository repository) {
        this.repository = repository;
    }

    public List<Place> getPlaceList(){
        return repository.findAll();
    }
}
