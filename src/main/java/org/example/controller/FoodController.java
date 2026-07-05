package org.example.controller;

import org.example.model.Food;
import org.example.model.responses.CarbonResponse;
import org.example.model.responses.FoodRowDTO;
import org.example.service.FoodService;
import org.example.service.item.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FoodController {
    private final FoodService service;
    private final FoodItemService foodItemService;

    public FoodController(FoodService service, FoodItemService foodItemService) {
        this.service = service;
        this.foodItemService = foodItemService;
    }

    @GetMapping("/food")
    public List<Food> findAllFood() {return service.getFoodList();}

    @GetMapping("/foodCarbon")
    public ResponseEntity<CarbonResponse> findCarbonValue(
            @RequestParam UUID foodId,
            @RequestParam double quantity
    ){
        CarbonResponse response = service.getCarbonValue(foodId,quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/liste/alimentation")
    public ResponseEntity<List<FoodRowDTO>> retrieveFood(
            @RequestParam UUID committeeId
    ){
        return ResponseEntity.ok(foodItemService.getFoodItemList(committeeId));
    }
}
