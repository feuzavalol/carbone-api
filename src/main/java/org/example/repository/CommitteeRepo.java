package org.example.repository;

import org.example.model.Category;
import org.example.model.Committee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CommitteeRepo extends JpaRepository<Committee, UUID> {
    Optional<Committee> findByCategoryAndNumber(Category category, int number);
    Optional<Committee> findCommitteeByCategory(Category category);

    Optional<Committee> findByYear(int year);
    Optional<Committee> findByNumber(int number);
}