package org.example.service;

import org.example.model.*;
import org.example.model.responses.CarbonResponse;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FoodService {

    private final FoodRepo foodRepo;
    private final CarbonService carbonService;

    public FoodService(FoodRepo foodRepo, CarbonService carbonService) {

        this.foodRepo = foodRepo;
        this.carbonService = carbonService;
    }

    public boolean existsById(UUID id){
        return foodRepo.existsById(id);
    }

    public List<Food> getFoodList() {return foodRepo.findAll();}

    public Food getFood(UUID foodId) {
        return foodRepo.findById(foodId)
                .orElseThrow(() -> new RuntimeException("There is no food matching this uuid: "+foodId));
    }

    public CarbonResponse getCarbonValue(UUID foodId, double quantity) {
        Food food = getFood(foodId);

        return new CarbonResponse(carbonService.getCarbonValue(food,quantity));
    }

    public Double getCostPerUnit(UUID foodId) {
        Food food = getFood(foodId);
        return food.getCost_per_unit();
    }

    public String getName(UUID foodId) {
        Food food = getFood(foodId);
        return food.getName();
    }
}
