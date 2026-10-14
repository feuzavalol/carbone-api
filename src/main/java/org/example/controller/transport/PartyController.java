package org.example.controller.transport;

import org.example.model.requests.transport.AddPartyRequest;
import org.example.model.responses.PartyRowDTO;
import org.example.repository.UserRepo;
import org.example.security.AuthenticatedUser;
import org.example.security.SecurityUtil;
import org.example.service.transport.PartyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PartyController {
    private final PartyService service;
    private final UserRepo userRepo;

    public PartyController(PartyService partyService, UserRepo userRepo) {
        this.service = partyService;
        this.userRepo = userRepo;
    }

    @GetMapping("/party")
    public ResponseEntity<Map<String, PartyRowDTO>> findParty(
            @RequestParam UUID committeeId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        if (SecurityUtil.checkUser(currentUser,committeeId)){
            PartyRowDTO result = service.getParty(committeeId);
            Map<String, PartyRowDTO> success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Vous n'avez pas le droit d'accéder à ces données", null));

    }

    @PostMapping("/addParty")
    public ResponseEntity<Map<String, PartyRowDTO>> addParty(
            @RequestBody AddPartyRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        // The user is supposed to be identified as a LIS user, through security filter in security config
        // Returns the added food items, even if it fails

        boolean busLegitimacy = SecurityUtil.checkAuthor(userRepo,request.item().busTrip(), currentUser);
        boolean carLegitimacy = SecurityUtil.checkAuthor(userRepo,request.item().carTrip(), currentUser);

        if (busLegitimacy && carLegitimacy){
            PartyRowDTO result = service.saveParty(request.item());

            Map<String, PartyRowDTO> success = Map.of("saved",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", null));
    }

}
