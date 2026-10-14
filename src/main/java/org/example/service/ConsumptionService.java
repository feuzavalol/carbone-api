package org.example.service;

import org.example.model.item.FoodItem;
import org.example.model.item.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsumptionService { // Adds a layer between Food, Transport and Good services and CarbonService
    private final CarbonService carbonService;
    private final FoodService foodService;
    private final GoodService goodService;

    public ConsumptionService(CarbonService carbonService, FoodService foodService, GoodService goodService) {
        this.carbonService = carbonService;
        this.foodService = foodService;
        this.goodService = goodService;
    }

    public List<Double> getCarbonValues(List<? extends Item> items, String itemCategory) {
        List<Double> carbonValues = new ArrayList<>();
        for (Item item : items) {
            switch (itemCategory) {
                case "food":
                    carbonValues.add(item.getQuantity()*foodService.getCostPerUnit(item.getObjectId()));
                    break;
                case "good":
                    carbonValues.add(item.getQuantity()*goodService.getCostPerUnit(item.getObjectId()));
                    break;
            }

        }
        return carbonValues;
    }

    public Double getTotalCost(List<FoodItem> items) {
        double result = 0.0;
        for (FoodItem foodItem : items) {
            result += foodItem.getQuantity()*foodService.getCostPerUnit(foodItem.getObjectId());
        }
        return result;
    }
}
