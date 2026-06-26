package org.example.repository;

import org.example.model.SubFoodGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubFoodGroupRepo extends JpaRepository<SubFoodGroup, UUID> {
}