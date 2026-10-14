package org.example.controller.transport;

import org.example.model.item.GoodItemDTO;
import org.example.model.requests.transport.AddTripRequest;
import org.example.model.requests.transport.RemoveTripRequest;
import org.example.model.responses.TripRowDTO;
import org.example.model.requests.transport.TripRequest;
import org.example.model.transport.Trip;
import org.example.repository.UserRepo;
import org.example.security.AuthenticatedUser;
import org.example.security.SecurityUtil;
import org.example.service.transport.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TripController {
    private final TripService service;
    private final UserRepo userRepo;

    public TripController(TripService tripService, UserRepo userRepo) {
        this.service = tripService;
        this.userRepo = userRepo;
    }

    @GetMapping("/carTrip")
    public ResponseEntity<Map<String,Object>> findAllTripNotParty(
            @RequestParam UUID committeeId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        if (SecurityUtil.checkUser(currentUser,committeeId)){
            TripRequest request = new TripRequest(committeeId);
            List<TripRowDTO> result = service.getTripList(request);
            result = service.sort(result); // The filled out ones appear on top
            int number = service.numberTripFilled(result);
            Map<String, Object> successData = new HashMap<>();
            successData.put("trips",result);
            successData.put("number",number);
            Map<String,Object> success = Map.of("success",successData);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Vous n'avez pas le droit d'accéder à ces données", new ArrayList<>()));

    }

    @PostMapping("/addTrips")
    public ResponseEntity<Map<String, List<TripRowDTO>>> addTrips(
            @RequestBody AddTripRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        // The user is supposed to be identified as a LIS user, through security filter in security config
        // Returns the added food items, even if it fails

        if (SecurityUtil.checkAuthors(userRepo,request.items(), currentUser)){
            List<TripRowDTO> result = service.saveTripItems(request.items());

            Map<String, List<TripRowDTO>> success = Map.of("saved",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", new ArrayList<>()));
    }

    @DeleteMapping("/removeTrip")
    public ResponseEntity<Map<String, TripRowDTO>> eraseTrip(
            @RequestBody RemoveTripRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        if (SecurityUtil.checkUser(currentUser, UUID.fromString(request.item().committeeId()))){
            try {
                TripRowDTO result = service.eraseTrip(request.item());
                Map<String, TripRowDTO> success = Map.of("removed",result);
                return ResponseEntity.ok(success);
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("Erreur : l'item que vous vouliez supprimer n'existe pas", request.item().toDTO()));
            }
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème",request.item().toDTO()));
    }

}
