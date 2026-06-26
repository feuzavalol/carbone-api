package org.example.controller;

import org.example.model.Food;
import org.example.model.responses.CarbonResponse;
import org.example.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @GetMapping("/food")
    public List<Food> findAllFood() {return service.getFoodList();}

    @GetMapping("/foodCarbon")
    public ResponseEntity<CarbonResponse> findCarbonValue(
            @RequestParam UUID foodId,
            @RequestParam double quantity
    ){
        return service.getCarbonValue(foodId,quantity);
    }
}
