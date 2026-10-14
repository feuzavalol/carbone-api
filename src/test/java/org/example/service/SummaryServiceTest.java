package org.example.service;

import org.example.model.item.FoodItem;
import org.example.model.item.Item;
import org.example.service.item.FoodItemService;
import org.example.service.item.GoodItemService;
import org.example.service.transport.TransportItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SummaryServiceTest {
    @Mock
    private FoodItemService foodItemService;

    @Mock
    private ConsumptionService consumptionService;

    @Mock
    private FoodService foodService;

    @Mock
    private MealTypeService mealTypeService;
    @Mock
    private TransportItemService transportItemService;
    @Mock
    private GoodItemService goodItemService;

    @InjectMocks
    private SummaryService service;

    @Test
    void getLabels() {

    }

    @Test
    void mergeDuplicates() {
        FoodItem item1 = new FoodItem(UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),10);
        FoodItem item2 = new FoodItem(UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),20);
        FoodItem merged1and2 = new FoodItem(UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),30);
        FoodItem item3 = new FoodItem(UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),UUID.fromString("fd9a712a-097f-4ef0-a770-7257e77427bc"),UUID.fromString("d0f4654e-5a3c-4dbc-9935-eb11d3582247"),5);
        List<FoodItem> foodItems = List.of(item1,item2,item3);
        List<Double> result = service.mergeDuplicates(foodItems).stream().map(Item::getQuantity).toList();
        assertThat(result).isEqualTo(Stream.of(merged1and2,item3).map(FoodItem::getQuantity).toList());
    }
}