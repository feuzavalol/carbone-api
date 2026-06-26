package org.example.service;

import org.example.model.*;
import org.example.model.requests.CarbonRequest;
import org.example.model.responses.CarbonResponse;
import org.example.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FoodService {

    private final FoodRepo foodRepo;

    public FoodService(FoodRepo foodRepo) {
        this.foodRepo = foodRepo;
    }

    public List<Food> getFoodList() {return foodRepo.findAll();}
    
    public ResponseEntity<CarbonResponse> getCarbonValue(UUID foodId, double quantity) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new RuntimeException("There is no food matching this uuid: "+foodId));

        return ResponseEntity.ok(new CarbonResponse(food.getCost_per_unit()*quantity));
    }
}
