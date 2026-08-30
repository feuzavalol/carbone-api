package org.example.service.transport;

import org.example.model.responses.CarbonResponse;
import org.example.model.transport.Transport;
import org.example.repository.transport.TransportRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class TransportService {

    private final TransportRepository transportRepo;

    public TransportService(TransportRepository transportRepo) {
        this.transportRepo = transportRepo;
    }

    public List<Transport> getTransportList() {return transportRepo.findAll();}

    public ResponseEntity<CarbonResponse> getCarbonValue(UUID transportId, double distance) {
        Transport transport = transportRepo.findById(transportId)
                .orElseThrow(() -> new RuntimeException("There is no transport matching this uuid: "+transportId));

        return ResponseEntity.ok(new CarbonResponse(transport.getCost_per_unit()*distance));
    }
}
