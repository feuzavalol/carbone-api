package org.example.service.transport;

import org.example.model.item.PartyDTO;
import org.example.model.responses.PartyRowDTO;
import org.example.model.responses.TripRowDTO;
import org.example.model.transport.*;
import org.example.repository.transport.PartyRepository;
import org.example.repository.transport.TripRepository;
import org.example.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartyService {
    private final PartyRepository repository;
    private final TripRepository tripRepository;
    private final TripService tripService;
    private final UsageService usageService;
    private final UserService userService;

    public PartyService(PartyRepository repository, TripRepository tripRepository, TripService tripService, UsageService usageService, UserService userService) {
        this.repository = repository;
        this.tripRepository = tripRepository;
        this.tripService = tripService;
        this.usageService = usageService;
        this.userService = userService;
    }



    private Trip findOrCreateTrip(UUID committeeId, UUID usageId) {
        Optional<Trip> existing = tripRepository.findByCommitteeIdAndUsageId(committeeId, usageId);
        if (existing.isPresent()) {
            return existing.get();
        }
        Trip newTrip = Trip.createEmptyTrip(committeeId, committeeId, usageId);
        try {
            return tripRepository.save(newTrip);
        } catch (DataIntegrityViolationException e) {
            // Lost the race — the winning transaction's row is what we want.
            return tripRepository.findByCommitteeIdAndUsageId(committeeId, usageId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Insert failed but no existing trip found for committee "
                                    + committeeId + " usage " + usageId, e));
        }
    }

    private List<Trip> generateTrip(UUID committeeId){
        // If trips are not found : saves two trip used for Party and returns them
        // Otherwise returns the found trip
        UUID busId = usageService.getUsageTypeId(String.valueOf(PartyCategory.PARTYBUS));
        UUID carId = usageService.getUsageTypeId(String.valueOf(PartyCategory.PARTYCAR));
        Trip busTrip = findOrCreateTrip(committeeId, busId);
        Trip carTrip = findOrCreateTrip(committeeId, carId);
        return List.of(busTrip, carTrip);
    }

    public PartyRowDTO getParty(UUID committeeId) {
        List<Trip> trips = generateTrip(committeeId);
        Trip busTrip = trips.get(0);
        Trip carTrip = trips.get(1);

        Optional<Party> party = repository.findByCommitteeId(committeeId);
        Party newParty;
        if (party.isEmpty()){
            newParty = new Party(committeeId, committeeId, null, busTrip.getId(), carTrip.getId());
            try {
                newParty = repository.save(newParty);
            } catch (DataIntegrityViolationException e) {
                newParty = repository.findByCommitteeId(committeeId)
                        .orElseThrow(() -> new IllegalStateException(
                                "Party insert failed but none found for committee " + committeeId, e));
            }
        } else {
            newParty = party.get();
        }

        PartyRowDTO result = new PartyRowDTO(newParty.getId(), newParty.getRouteId(), tripService.toDTO(busTrip), tripService.toDTO(carTrip));
        return result;
    }

    public PartyRowDTO toRowDTO(Party savedParty) {
        TripRowDTO busTripRowDTO = tripService.rowDTOFromUUID(savedParty.getBusTripId());
        TripRowDTO carTripRowDTO = tripService.rowDTOFromUUID(savedParty.getCarTripId());
        PartyRowDTO result = new PartyRowDTO(savedParty.getId(), savedParty.getRouteId(),busTripRowDTO,carTripRowDTO);
        return result;
    }

    public PartyRowDTO saveParty(PartyDTO request) {
        tripService.saveTripItem(request.busTrip());
        tripService.saveTripItem(request.carTrip());
        UUID id = userService.parseId(request.id());
        Party item;
        if (id != null){
            Optional<Party> previousItem = repository.findById(id);
            if (previousItem.isEmpty()){ // The item doesn't exist in the DB
                item = new Party(request); // We create the new item with a UUID generated later by Hibernate
            }
            else {
                // The item has to be modified, so we create a food item with the same id as the previous one with the data from the dto
                item = Party.createPartyWithId(request);
            }
        }
        else { // The item provided doesn't have id
            item = new Party(request);
        }
        Party savedParty= repository.save(item);
        return toRowDTO(savedParty);
    }
}
