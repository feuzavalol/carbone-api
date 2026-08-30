package org.example.service.transport;

import org.example.model.transport.Trip;
import org.example.model.requests.TripRequest;
import org.example.repository.transport.TripRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TripService {
    private final TripRepository repository;

    public TripService(TripRepository repository) {
        this.repository = repository;
    }

    public List<Trip> getTripsByCommitteeId(UUID committeeId){
        return repository.findByCommitteeId(committeeId).orElse(new java.util.ArrayList<>(List.of()));
    }

    public List<Trip> getTripList(TripRequest request){
        // This function is used by the UI to fetch already saved trips made by the committee
        // If there is no element in the database yet, it returns an empty list
        UUID committeeId = request.committeeId();
        String usage = request.usage();
        // System.out.println(committeeId);
        return repository.findByCommitteeIdAndUsage(committeeId, usage)
                .orElse(new java.util.ArrayList<>(List.of()));
    }

}
