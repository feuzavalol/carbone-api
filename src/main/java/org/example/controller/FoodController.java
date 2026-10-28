package org.example.controller;

import org.example.model.*;
import org.example.model.categories.MealType;
import org.example.model.requests.food.CreateFoodRequest;
import org.example.model.requests.food.ModifyFoodRequest;
import org.example.model.requests.food.RemoveFoodRequest;
import org.example.model.responses.CarbonResponse;
import org.example.security.AuthenticatedUser;
import org.example.security.SecurityUtil;
import org.example.service.MealTypeService;
import org.example.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/foodGroup")
    public ResponseEntity<Map<String,List<String>>> findAllFoodGroup(){
        List<String> foodGroups = service.getFoodGroups();

        Map<String, List<String>> success = Map.of("success", foodGroups);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/subFoodGroup")
    public ResponseEntity<Map<String,List<String>>> findAllSubFoodGroup(){
        List<String> subFoodGroups = service.getSubFoodGroups();

        Map<String, List<String>> success = Map.of("success", subFoodGroups);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/units")
    public ResponseEntity<Map<String,List<String>>> findAllFoodExpectedUnits(){
        List<String> units = FoodUnit.allValues();

        Map<String, List<String>> success = Map.of("success",units);
        return ResponseEntity.ok(success);
    }

    private String message(String verb){
        return "Erreur : l'auteur de cette requête n'a pas le pouvoir de faire ça\nL'auteur a tenté de " + verb + " un aliment de la base de données";
    }

    @PostMapping("/modifyFood")
    public ResponseEntity<Map<String,Food>> modifyFood(
            @RequestBody ModifyFoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ){
        if (SecurityUtil.isUserAdmin(currentUser)){
            Food result = service.modifyFood(request.getFood());
            Map<String,Food> success;
            success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(message("modifier"), new Food()));
    }

    @PostMapping("/createFood")
    public ResponseEntity<Map<String,Food>> createFood(
            @RequestBody CreateFoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ){
        if (SecurityUtil.isUserAdmin(currentUser)){
            Food result = service.createFood(request.getFood());
            Map<String,Food> success;
            success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(message("créer"), new Food()));
    }
    @DeleteMapping("/removeFood")
    public ResponseEntity<Map<String,Food>> removeFood(
            @RequestBody RemoveFoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ){
        System.out.println(currentUser.role());
        if (SecurityUtil.isUserAdmin(currentUser)){
            Food result = service.removeFood(request.getFood());
            Map<String,Food> success;
            success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(message("retirer"), new Food()));
    }
}
