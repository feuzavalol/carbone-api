package org.example.controller;

import org.example.model.item.FoodItem;
import org.example.model.requests.AddFoodRequest;
import org.example.model.requests.FoodRequest;
import org.example.service.item.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FoodItemController {
    private final FoodItemService service;

    public FoodItemController(FoodItemService service) {
        this.service = service;
    }

    public boolean checkAuthors(List<FoodRequest> requests){
        String firstAuthorName = requests.get(0).getAuthorName();
        for(FoodRequest request : requests){
            if (!Objects.equals(firstAuthorName, request.getAuthorName())){
                return false;
            }
        }
        return true;
    }

    @GetMapping("/foodItem")
    public List<FoodItem> findAllFoodItem(@RequestBody String username) {return service.getFoodItemList(username);}

    @PostMapping("/addFoodItems")
    public ResponseEntity<Map<String, List<FoodRequest>>> addFoodItems(@RequestBody AddFoodRequest request) {
        // The user is supposed to be identified as a LIS user, through security filter in security config
        // Returns the added food items, even if it fails

        if (!checkAuthors(request.getFoodRequestList())){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error : PLusieurs auteurs ont effectués des modifications", request.getFoodRequestList()));
        }
        for(FoodRequest foodRequest : request.getFoodRequestList()){
            service.createAndSaveFoodItem(foodRequest);
        }


        Map<String, List<FoodRequest>> success = Map.of("Food item",request.getFoodRequestList());
        return ResponseEntity.ok(success);
    }
}
