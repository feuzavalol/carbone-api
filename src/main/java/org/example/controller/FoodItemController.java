package org.example.controller;

import org.example.model.User;
import org.example.model.item.FoodItem;
import org.example.model.item.FoodItemDTO;
import org.example.model.requests.AddFoodRequest;
import org.example.model.requests.FoodItemRequest;
import org.example.model.responses.FoodRowDTO;
import org.example.repository.UserRepo;
import org.example.security.SecurityVariables;
import org.example.service.item.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public boolean checkAuthors(List<FoodItemDTO> requests){
        for(FoodItemDTO request : requests){
            UUID authorId = UUID.fromString(request.authorId());
            UUID committeeId = UUID.fromString(request.committeeId());
            if (!userRepo.existsById(authorId)){
                return false;
            }
            else {
                User author = userRepo.findById(authorId).get();
                if (author.getCommittee_id() != committeeId
                        && !SecurityVariables.authorizedRoles.contains(author.getRole().toString())){
                    return false;
                }
            }
        }
        return true;
    }

    @GetMapping("/foodItem")
    public ResponseEntity<List<FoodRowDTO>> findAllFoodItem(@RequestParam UUID committeeId) {
        FoodItemRequest request = new FoodItemRequest(committeeId,"");
        List<FoodRowDTO> result = service.getFoodItemList(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/addFoodItems")
    public ResponseEntity<Map<String, List<FoodRowDTO>>> addFoodItems(@RequestBody AddFoodRequest request) {
        // The user is supposed to be identified as a LIS user, through security filter in security config
        // Returns the added food items, even if it fails

        if (!checkAuthors(request.getItems())){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error : Un ou plusieurs auteurs de modifications ont posé problème", new ArrayList<>()));
        }
        List<FoodRowDTO> result = service.saveFoodItems(request.getItems());


        Map<String, List<FoodRowDTO>> success = Map.of("saved",result);
        return ResponseEntity.ok(success);
    }
}
