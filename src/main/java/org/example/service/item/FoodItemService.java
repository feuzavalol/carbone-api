package org.example.service.item;

import org.example.model.*;
import org.example.model.item.FoodItem;
import org.example.model.requests.FoodRequest;
import org.example.model.responses.FoodRowDTO;
import org.example.repository.*;
import org.example.repository.item.FoodItemRepo;
import org.example.service.CarbonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FoodItemService {

    private final FoodItemRepo repository;
    private final FoodRepo foodRepo;
    private final UserRepo userRepo;
    private final CarbonService carbonService;

    // Spring injects the repositories automatically via the constructor
    public FoodItemService(FoodItemRepo foodItemRepo, FoodRepo foodRepo, UserRepo userRepo, CarbonService carbonService) {
        this.repository = foodItemRepo;
        this.foodRepo = foodRepo;
        this.userRepo = userRepo;
        this.carbonService = carbonService;
    }

    public FoodItem createFoodItem(FoodRequest request) {
        Food food = foodRepo
                .findByName(request.getFoodName())
                .orElseThrow(() -> new RuntimeException("Food not found or too many food found: " + request.getFoodName() ));

        User author = userRepo
                .findByName(request.getAuthorName())
                .orElseThrow(() -> new RuntimeException("Author for this request not found: " + request.getAuthorName()));

        return new FoodItem(author.getId(),"FOOD", food.getId(), request.getValue());
    }

    @Transactional
    public void createAndSaveFoodItem(FoodRequest request) {
        FoodItem foodItem = createFoodItem(request);
        repository.save(foodItem);
    }

    public List<FoodRowDTO> getFoodItemList(UUID committeeID){
        // This function is used by the UI to fetch already saved carbon footprint
        // If there is no element in the database yet, it returns an empty list
        List<FoodItem> foodItems = repository.findByAuthorId(committeeID)
                .orElse(new java.util.ArrayList<>(List.of()));
        List<FoodRowDTO> result = new java.util.ArrayList<>(List.of());
        for(FoodItem f : foodItems){
            Food temp = foodRepo.findById(f.getObjectId()).orElseThrow(() -> new RuntimeException("Couldn't find any food for this uuid: " + f.getObjectId()));
            double carbonValue = carbonService.getCarbonValue(temp,f.getQuantity());
            result.add(new FoodRowDTO(temp.getName(),temp.getFood_group(),f.getQuantity(),carbonValue));
        }
        return result;
    }
}
