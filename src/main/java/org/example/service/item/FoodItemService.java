package org.example.service.item;

import org.example.model.*;
import org.example.model.item.FoodItem;
import org.example.model.requests.FoodRequest;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FoodItemService {

    private final FoodItemRepo repository;
    private final FoodRepo foodRepo;
    private final UserRepo userRepo;

    // Spring injects the repositories automatically via the constructor
    public FoodItemService(FoodItemRepo foodItemRepo, FoodRepo foodRepo, UserRepo userRepo) {
        this.repository = foodItemRepo;
        this.foodRepo = foodRepo;
        this.userRepo = userRepo;
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

    public List<FoodItem> getFoodItemList(String authorName){
        User author = userRepo
                .findByName(authorName)
                .orElseThrow(() -> new RuntimeException("Author for this request not found: " + authorName));

        return repository
                .findByAuthorId(author.getId())
                .orElseThrow(() -> new RuntimeException("Couldn't find any food items for this author: " + authorName))
                ;
    }
}
