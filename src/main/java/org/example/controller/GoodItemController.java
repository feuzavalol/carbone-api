package org.example.controller;

import org.example.model.item.GoodItemDTO;
import org.example.model.requests.*;
import org.example.model.responses.GoodRowDTO;
import org.example.repository.UserRepo;
import org.example.security.AuthenticatedUser;
import org.example.service.item.GoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.example.security.SecurityUtil.*;

@RestController
public class GoodItemController {
    private final GoodItemService service;
    private final UserRepo userRepo;

    public GoodItemController(GoodItemService service, UserRepo userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

    @GetMapping("/goodItem")
    public ResponseEntity<Map<String,List<GoodRowDTO>>> findAllGoodItem(
            @RequestParam UUID committeeId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        if (checkUser(currentUser,committeeId)){
            GoodItemRequest request = new GoodItemRequest(committeeId);
            List<GoodRowDTO> result = service.getGoodItemList(request);
            Map<String, List<GoodRowDTO>> success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Vous n'avez pas le droit d'accéder à ces données", new ArrayList<>()));

    }

    @PostMapping("/addGoodItems")
    public ResponseEntity<Map<String, List<GoodRowDTO>>> addGoodItems(
            @RequestBody AddGoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        // The user is supposed to be identified as a LIS user, through security filter in security config
        // Returns the added good items, even if it fails

        if (checkAuthors(userRepo,request.getItems(), currentUser)){
            List<GoodRowDTO> result = service.saveGoodItems(request.getItems());

            Map<String, List<GoodRowDTO>> success = Map.of("saved",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", new ArrayList<>()));
    }

    @DeleteMapping("/removeGoodItems")
    public ResponseEntity<Map<String,GoodItemDTO>> removeGoodItem(
            @RequestBody RemoveGoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ){
        // The user is supposed to be identified as a LIS user, through security filter in security config
        if (checkAuthor(userRepo,request.getItem(), currentUser)){
            try {
                GoodItemDTO result = service.removeGoodItem(request.getItem());
                Map<String, GoodItemDTO> success = Map.of("removed",result);
                return ResponseEntity.ok(success);
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("Erreur : l'item que vous vouliez supprimer n'existe pas", new GoodItemDTO("","","","",0.0)));
            }
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", new GoodItemDTO("","","","",0.0)));

    }
}
