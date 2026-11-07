package org.example.service;

import org.example.model.categories.MealType;
import org.example.repository.*;
import org.springframework.stereotype.Service;

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

    public List<MealType> findAllNonListe(){
        List<MealType> result = mealTypeRepository.findAll();
        return result.stream().filter(MealType::getIsNonListe).toList();
    }

    public MealType findById(UUID id) {
        return mealTypeRepository.findById(id).orElse(null);
    }

    public List<MealType> findById(List<UUID> ids) {
        return ids.stream().map((UUID id) -> mealTypeRepository.findById(id).orElse(null)).toList();
    }

    public List<String> getNameForMealListe() {
        List<MealType> result = findAllListe();
        return result.stream().map(MealType::getName).toList();
    }

    public MealType getFirstMeal(List<MealType> mealTypes){
        MealType result = mealTypes.get(0);
        System.out.println(mealTypes);
        for (MealType mealType : mealTypes){
            if (mealType.lowerThan(result)){
                System.out.println(result + " -> " + mealType);
                result = mealType;
            }
        }
        return result;
    }
}
