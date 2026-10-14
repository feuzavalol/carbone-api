package org.example.repository;

import org.example.model.categories.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MealTypeRepository extends JpaRepository<MealType, UUID> {

}
