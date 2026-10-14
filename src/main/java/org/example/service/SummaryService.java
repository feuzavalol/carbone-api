package org.example.service;

import org.example.model.ColorTools;
import org.example.model.item.FoodItem;
import org.example.model.item.GoodItem;
import org.example.model.item.Item;
import org.example.model.responses.Dataset;
import org.example.model.responses.SummaryDTO;
import org.example.service.item.FoodItemService;
import org.example.service.item.GoodItemService;
import org.example.service.transport.TransportItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SummaryService {
    private final FoodItemService foodItemService;
    private final ConsumptionService consumptionService;
    private final FoodService foodService;
    private final MealTypeService mealTypeService;
    private final TransportItemService transportItemService;
    private final GoodItemService goodItemService;
    private final GoodService goodService;
    public final String mainLabel = "kgC02éq";

    public SummaryService(FoodItemService foodItemService, ConsumptionService consumptionService,
                          FoodService foodService, MealTypeService mealTypeService,
                          TransportItemService transportItemService, GoodItemService goodItemService, GoodService goodService) {
        this.foodItemService = foodItemService;
        this.consumptionService = consumptionService;
        this.foodService = foodService;
        this.mealTypeService = mealTypeService;
        this.transportItemService = transportItemService;
        this.goodItemService = goodItemService;
        this.goodService = goodService;
    }

    public List<String> getLabels (List<? extends Item> items, String itemCategory) {
        List<String> labels = new ArrayList<>();
        for (Item item : items) {
            switch (itemCategory) {
                case "food":
                    labels.add(foodService.getName(item.getObjectId()));
                    break;
                case "good":
                    labels.add(goodService.getName(item.getObjectId()));
            }

        }
        return labels;
    }

    public List<Item> mergeDuplicates(List<? extends Item> items) {
        List<Item> mergedItems = new ArrayList<>();
        for (Item item : items) {
            boolean hasToBeAdded = true;
            for (Item existingItem : mergedItems) {
                if (item.getObjectId().equals(existingItem.getObjectId())) {
                    hasToBeAdded = false;
                    existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                }
            }
            if (hasToBeAdded) {
                mergedItems.add(item);
            }
        }
        return mergedItems;
    }

    public SummaryDTO getFoodSummary(UUID committeeId) {
        List<FoodItem> items = foodItemService.getFoodItemList(committeeId);
        List<Double> carbonValues = consumptionService.getCarbonValues(items, "food");
        List<Dataset> datasets = new ArrayList<>();
        datasets.add(new Dataset(mainLabel, carbonValues, ColorTools.getRandomColors(carbonValues.size())));

        return new SummaryDTO(getLabels(items, "food"),datasets);
    }

    public SummaryDTO getMealSummary(UUID committeeId) {
        List<List<FoodItem>> itemsByMeal = foodItemService.getFoodItemListOrganizedByMealTypeForListe(committeeId);
        List<Double> carbonValues = new ArrayList<>();
        for (List<FoodItem> foodItems : itemsByMeal) {
            Double totalCost = consumptionService.getTotalCost(foodItems);
            carbonValues.add(totalCost);
        }

        List<Dataset> datasets = new ArrayList<>();
        // List<String> colors = ColorTools.getRandomColors(carbonValues.size());
        List<String> colors = ColorTools.staticRandomColors(carbonValues.size());
        datasets.add(new Dataset(mainLabel,carbonValues,colors));

        return new SummaryDTO(mealTypeService.getLabelForMealListe(),datasets);
    }

//    public SummaryDTO getTransportSummary(UUID committeeId) {
//        List<TransportRowDTO> transportItems = transportItemService.getTransportItemList(committeeId);
//        return new SummaryDTO();
//    }

    public SummaryDTO getGoodsSummary(UUID committeeId) {
        List<GoodItem> goodItems = goodItemService.getGoodItemList(committeeId);
        // List<Item> items = mergeDuplicates(goodItems);
        List<Double> carbonValues = consumptionService.getCarbonValues(goodItems, "good");
        List<String> colors = ColorTools.staticRandomColors(carbonValues.size());
        List<Dataset> datasets = List.of(new Dataset(mainLabel,carbonValues,colors));
        return new SummaryDTO(getLabels(goodItems, "good"),datasets);
    }
}
