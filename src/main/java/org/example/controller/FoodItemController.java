package org.example.controller;

import org.example.model.item.FoodItemDTO;
import org.example.model.requests.food.AddFoodRequest;
import org.example.model.requests.food.FoodItemRequest;
import org.example.model.requests.food.RemoveFoodRequest;
import org.example.model.responses.FoodRowDTO;
import org.example.repository.UserRepo;
import org.example.security.AuthenticatedUser;
import org.example.security.SecurityUtil;
import org.example.service.item.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FoodItemController {
    private final FoodItemService service;
    private final UserRepo userRepo;

    public FoodItemController(FoodItemService service, UserRepo userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

    @GetMapping("/foodItem")
    public ResponseEntity<Map<String,List<FoodRowDTO>>> findAllFoodItem(
            @RequestParam UUID committeeId,
            @RequestParam UUID categoryId,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        if (SecurityUtil.checkUser(currentUser,committeeId)){
            FoodItemRequest request = new FoodItemRequest(committeeId,categoryId);
            List<FoodRowDTO> result = service.getFoodItemList(request);
            Map<String, List<FoodRowDTO>> success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("Erreur : Vous n'avez pas le droit d'accéder à ces données", new ArrayList<>()));

    }

    @PostMapping("/addFoodItems")
    public ResponseEntity<Map<String, List<FoodRowDTO>>> addFoodItems(
            @RequestBody AddFoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ) {
        // The user is supposed to be identified as a LIS user, through security filter in security config
        // Returns the added food items, even if it fails

        if (SecurityUtil.checkAuthors(userRepo,request.getItems(), currentUser)){
            List<FoodRowDTO> result = service.saveFoodItems(request.getItems());

            Map<String, List<FoodRowDTO>> success = Map.of("saved",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", new ArrayList<>()));
    }

    @DeleteMapping("/removeFoodItems")
    public ResponseEntity<Map<String,FoodItemDTO>> removeFoodItem(
            @RequestBody RemoveFoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ){
        // The user is supposed to be identified as a LIS user, through security filter in security config
        if (SecurityUtil.checkAuthor(userRepo,request.getItem(), currentUser)){
            try {
                FoodItemDTO result = service.removeFoodItem(request.getItem());
                Map<String, FoodItemDTO> success = Map.of("removed",result);
                return ResponseEntity.ok(success);
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("Erreur : l'item que vous vouliez supprimer n'existe pas", new FoodItemDTO("","","","","",0.0)));
            }
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", new FoodItemDTO("","","","","",0.0)));
    }
}
