package org.example.service;

import org.example.model.categories.MealType;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class MealTypeService {

    private final MealTypeRepository mealTypeRepository;

    public MealTypeService(MealTypeRepository mealTypeRepository) {
        this.mealTypeRepository = mealTypeRepository;
    }

    public  List<MealType> findAll() {
        return mealTypeRepository.findAll();
    }

    private boolean is_liste(MealType category){
        return category.getIsListe();
    }

    public List<MealType> findAllListe(){
        List<MealType> result = mealTypeRepository.findAll();
        return result.stream().filter(MealType::getIsListe).toList();
    }

    public MealType findById(UUID id) {
        return mealTypeRepository.findById(id).orElse(null);
    }

    public List<String> getLabelForMealListe() {
        List<MealType> result = findAllListe();
        return result.stream().map(MealType::getName).toList();
    }
}
