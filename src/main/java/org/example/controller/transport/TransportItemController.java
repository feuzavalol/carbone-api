package org.example.controller.transport;

import org.example.model.requests.transport.AddTransportRequest;
import org.example.model.responses.TransportRowDTO;
import org.example.repository.UserRepo;
import org.example.security.AuthenticatedUser;
import org.example.service.transport.TransportService;
import org.example.service.transport.TransportItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.example.security.SecurityUtil.checkAuthors;
import static org.example.security.SecurityUtil.checkUser;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class TransportItemController {
    private final TransportService transportService;
    private final TransportItemService service;
    private final UserRepo userRepo;

    public TransportItemController(TransportItemService service, TransportService transportService, UserRepo userRepo) {
        this.service = service;
        this.transportService = transportService;
        this.userRepo = userRepo;
    }

    @GetMapping("/transportItem")
    public ResponseEntity<Map<String,List<TransportRowDTO>>> findAllTransportByCommitteeId(
            @RequestParam UUID committeeId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        if (checkUser(currentUser,committeeId)) {
            List<TransportRowDTO> result = service.getTransportItemList(committeeId);
            Map<String,List<TransportRowDTO>> success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Vous n'avez pas le droit d'accéder à ces données", new ArrayList<>()));
    }

    @PostMapping("/addTransportItems")
    public ResponseEntity<Map<String, List<TransportRowDTO>>> addTransportItems(
            @RequestBody AddTransportRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        if (checkAuthors(userRepo,request.getItems(), currentUser)) {
            List<TransportRowDTO> result = service.saveTransportItems(request.getItems());

            Map<String, List<TransportRowDTO>> success = Map.of("saved", result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Vous n'avez pas le droit de modifier ces données", new ArrayList<>()));
    }

}
