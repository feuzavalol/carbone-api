package org.example.service;

import org.example.model.*;
import org.example.model.requests.CarbonRequest;
import org.example.model.responses.CarbonResponse;
import org.example.repository.*;
        import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransportService {

    private final TransportRepo transportRepo;

    public TransportService(TransportRepo transportRepo) {
        this.transportRepo = transportRepo;
    }

    public List<Transport> getTransportList() {return transportRepo.findAll();}

    public ResponseEntity<CarbonResponse> getCarbonValue(UUID transportId, double distance) {
        Transport transport = transportRepo.findById(transportId)
                .orElseThrow(() -> new RuntimeException("There is no food matching this uuid: "+transportId));

        return ResponseEntity.ok(new CarbonResponse(transport.getCost_per_unit()*distance));
    }
}
