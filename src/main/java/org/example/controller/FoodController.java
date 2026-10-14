package org.example.controller;

import org.example.model.Food;
import org.example.model.categories.MealType;
import org.example.model.responses.CarbonResponse;
import org.example.service.MealTypeService;
import org.example.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class FoodController {
    private final FoodService service;
    private final MealTypeService mealTypeService;

    public FoodController(FoodService service, MealTypeService mealTypeService) {
        this.service = service;
        this.mealTypeService = mealTypeService;
    }

    @GetMapping("/singleFood")
    public Food findFood(@RequestParam UUID foodId){
        return service.getFood(foodId);
    }

    @GetMapping("/food")
    public List<Food> findAllFood() {return service.getFoodList();}

    @GetMapping("/foodCarbon")
    public ResponseEntity<CarbonResponse> findCarbonValue(
            @RequestParam UUID foodId,
            @RequestParam double quantity
    ){
        System.out.println("Someone is trying to fetch carbon value for: "+foodId+" and quantity: "+quantity);
        CarbonResponse response = service.getCarbonValue(foodId,quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/foodCategories")
    public ResponseEntity<List<MealType>> findAllFoodCategories(){
        return ResponseEntity.ok(mealTypeService.findAll());
    }

    @GetMapping("/listeFoodCategories")
    public ResponseEntity<List<MealType>> findAllFoodCategoriesForListe(){
        return ResponseEntity.ok(mealTypeService.findAllListe());
    }

    @GetMapping("/foodCategory")
    public ResponseEntity<MealType> findFoodCategory(
            @RequestParam UUID id
    ){
        return ResponseEntity.ok(mealTypeService.findById(id));
    }
}
