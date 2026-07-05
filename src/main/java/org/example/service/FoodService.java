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
    private final CarbonService carbonService;

    public FoodService(FoodRepo foodRepo, CarbonService carbonService) {

        this.foodRepo = foodRepo;
        this.carbonService = carbonService;
    }

    public List<Food> getFoodList() {return foodRepo.findAll();}
    
    public CarbonResponse getCarbonValue(UUID foodId, double quantity) {
        Food food = foodRepo.findById(foodId)
                .orElseThrow(() -> new RuntimeException("There is no food matching this uuid: "+foodId));

        return new CarbonResponse(carbonService.getCarbonValue(food,quantity));
    }
}
