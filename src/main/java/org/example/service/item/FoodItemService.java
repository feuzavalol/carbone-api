package org.example.service.item;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.*;
import org.example.model.item.FoodItem;
import org.example.model.item.FoodItemDTO;
import org.example.model.requests.FoodItemRequest;
import org.example.model.responses.FoodRowDTO;
import org.example.repository.*;
import org.example.repository.item.FoodItemRepo;
import org.example.service.CarbonService;
import org.example.service.FoodService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FoodItemService {

    private final FoodItemRepo repository;
    private final FoodService foodService;
    private final UserRepo userRepo;
    private final CommitteeRepo committeeRepo;
    private final CarbonService carbonService;

    // Spring injects the repositories automatically via the constructor
    public FoodItemService(FoodItemRepo foodItemRepo, FoodService foodService, UserRepo userRepo, CommitteeRepo committeeRepo, CarbonService carbonService) {
        this.repository = foodItemRepo;
        this.foodService = foodService;
        this.userRepo = userRepo;
        this.committeeRepo = committeeRepo;
        this.carbonService = carbonService;
    }

    private boolean checkFoodRequest(FoodItem request){
        return foodService.existsById(request.getObjectId()) && userRepo.existsById(request.getAuthorId()) && committeeRepo.existsById(request.getCommitteeId());
    }

    @Transactional
    public void createAndSaveFoodItem(FoodItem foodItem) {
        repository.save(foodItem);
    }

    public FoodRowDTO createFoodRowDTO(FoodItem item){
        Food food = foodService.getFood(item.getObjectId());
        double co2Value = carbonService.getCarbonValue(food,item.getQuantity());
        return new FoodRowDTO(item.getId(),food.getId(),food.getName(),item.getCategory(),item.getQuantity(),co2Value);
    }

    private UUID parseId(String rawId) {
        return (rawId == null || rawId.isBlank()) ? null : UUID.fromString(rawId);
    }

    public List<FoodRowDTO> saveFoodItems(List<FoodItemDTO> foodItemDTOS){
        List<FoodRowDTO> result = new ArrayList<>();
        for (FoodItemDTO dto : foodItemDTOS){
            UUID id = parseId(dto.id());
            FoodItem item = (id != null)
                    ? repository.findById(id).orElse(new FoodItem(dto))
                    : new FoodItem(dto);
            System.out.println(item);
            FoodItem savedItem = repository.save(item);

            result.add(createFoodRowDTO(savedItem));
        }
        return result;
    }

    public FoodItemDTO removeFoodItem(FoodItemDTO dto){
        UUID id = parseId(dto.id());
        if (id != null){
            if (repository.existsById(id)){
                repository.deleteById(id);
                return dto;
            }
            else{
                throw new EntityNotFoundException("The item you wanted to remove doesn't exist");
            }
        }
        else{
            throw new RuntimeException("The id provided is null");
        }
    }

    public List<FoodRowDTO> getFoodItemList(FoodItemRequest request){
        // This function is used by the UI to fetch already saved carbon footprint
        // If there is no element in the database yet, it returns an empty list
        UUID committeeId = request.getCommitteeId();
        String category = request.getCategory();
        // System.out.println(committeeId);
        List<FoodItem> foodItems = repository.findByCommitteeIdAndCategory(committeeId, category)
                .orElse(new java.util.ArrayList<>(List.of()));
        List<FoodRowDTO> result = new java.util.ArrayList<>(List.of());
        for(FoodItem f : foodItems){
            Food food = foodService.getFood(f.getObjectId());
            double carbonValue = carbonService.getCarbonValue(food,f.getQuantity());
            result.add(new FoodRowDTO(f.getId(),food.getId(),food.getName(),request.getCategory(),f.getQuantity(),carbonValue));
        }
        // System.out.println(result);
        return result;
    }
}
