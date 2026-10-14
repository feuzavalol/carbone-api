package org.example.service.transport;

import org.example.model.item.TripDTO;
import org.example.model.responses.TripRowDTO;
import org.example.model.transport.Trip;
import org.example.model.requests.transport.TripRequest;
import org.example.repository.transport.TripRepository;
import org.example.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class TripService {
    private final TripRepository repository;
    private final UsageService usageService;
    private final UserService userService;

    public TripService(TripRepository repository, UsageService usageService, UserService userService) {
        this.repository = repository;
        this.usageService = usageService;
        this.userService = userService;
    }

    public List<Trip> getTripsByCommitteeId(UUID committeeId){
        return repository.findByCommitteeId(committeeId).orElse(new java.util.ArrayList<>(List.of()));
    }

    private List<TripRowDTO> toDTO(List<Trip> trips){
        List<TripRowDTO> result = new java.util.ArrayList<>(List.of());
        for(Trip trip : trips){
            result.add(toDTO(trip));
        }
        return result;
    }

    private boolean is_empty(TripRowDTO tripRowDTO){
        return tripRowDTO.quantity() == 0 && tripRowDTO.consumption() == 0 && tripRowDTO.energy() == null;
    }

    public int numberTripFilled(List<TripRowDTO> list) {
        int result = 0;
        for (TripRowDTO tripRowDTO : list) {
            result += is_empty(tripRowDTO) ? 0 : 1;
        }
        return result;
    }

    private Trip findOrCreateTrip(UUID committeeId, UUID usageId) {
        Optional<Trip> existing = repository.findByCommitteeIdAndUsageId(committeeId, usageId);
        if (existing.isPresent()) {
            return existing.get();
        }
        Trip newTrip = Trip.createEmptyTrip(committeeId, committeeId, usageId);
        try {
            return repository.save(newTrip);
        } catch (DataIntegrityViolationException e) {
            // Lost the race — the winning transaction's row is what we want.
            return repository.findByCommitteeIdAndUsageId(committeeId, usageId)
                    .orElseThrow(() -> new IllegalStateException(
                            "Insert failed but no existing trip found for committee "
                                    + committeeId + " usage " + usageId, e));
        }
    }

    private List<Trip> generateTrip(UUID committeeId){
        // If trips are not found : saves two trip used for Party and returns them
        // Otherwise returns the found trip
        List<String> names = IntStream.rangeClosed(1, 20).mapToObj(i -> "TRIP" + i).toList();
        List<Trip> result = new ArrayList<>();
        for (String name : names) {
            UUID id = usageService.getUsageTypeId(name);
            Trip trip = findOrCreateTrip(committeeId, id);
            result.add(trip);
        }
        return result;
    }

    public List<TripRowDTO> getTripList(TripRequest request){ // Returns all the trip a committee possess, without those who are not authorized
        // Creates trips if needed
        UUID committeeId = request.committeeId();
        List<Trip> trips = generateTrip(committeeId);
        // Other usages must be fetched from PartyService and PartyController
        trips.removeIf(trip -> !usageService.is_authorized(usageService.getUsageType(trip.getUsageId())));
        return toDTO(trips);
    }

    TripRowDTO toDTO(Trip trip) {
        return new TripRowDTO(trip.getId(),trip.getUsageId(),trip.getEnergy(),trip.getConsumption(),trip.getQuantity(),trip.getComment());
    }

    public TripRowDTO rowDTOFromUUID(UUID uuid) {
        Trip busTrip = repository.findById(uuid).orElseThrow(() -> new RuntimeException("There is no trip matching this uuid: "+uuid));
        return toDTO(busTrip);
    }

    public Trip saveTripItem(TripDTO dto){
        UUID id = userService.parseId(dto.id());
        Trip item;
        if (id != null){ //
            Optional<Trip> previousItem = repository.findById(id);
            if (previousItem.isEmpty()){ // The item doesn't exist in the DB
                item = new Trip(dto); // We create the new item with a UUID generated later by Hibernate
            }
            else {
                // The item has to be modified, so we create a food item with the same id as the previous one with the data from the dto
                item = Trip.createTripWithId(dto);
            }
        }
        else { // The item provided doesn't have id
            item = new Trip(dto);
        }
//        System.out.println("Here is the item we want to save: ");
//        System.out.println(item);
        Trip savedItem = repository.save(item);
//        System.out.println("Here is the item we tried to save: ");
//        System.out.println(savedItem);
        return savedItem;
    }

    public List<TripRowDTO> saveTripItems(List<TripDTO> items) {
        
        List<TripRowDTO> result = new ArrayList<>();
        for (TripDTO dto : items){
            Trip savedItem = saveTripItem(dto);
            result.add(toDTO(savedItem));
        }
        return result;
        
    }

    public List<TripRowDTO> sort(List<TripRowDTO> result) {
        result.sort(Comparator.comparing(this::is_empty));
        return result;
    }

    public TripRowDTO eraseTrip(TripDTO item) {
        Trip fetchedTrip = repository.findById(UUID.fromString(item.id())).orElseThrow(() -> new RuntimeException("There is no trip matching this id: "+item.id()));
        Trip erasedTrip = Trip.createEmptyTrip(fetchedTrip);
        return toDTO(repository.save(erasedTrip));
    }
}
