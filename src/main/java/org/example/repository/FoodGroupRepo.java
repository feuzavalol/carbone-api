package org.example.repository;

import org.example.model.FoodGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodGroupRepo extends JpaRepository<FoodGroup, UUID> {

}