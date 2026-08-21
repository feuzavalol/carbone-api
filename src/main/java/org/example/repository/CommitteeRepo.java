package org.example.repository;

import jakarta.annotation.Nonnull;
import org.example.model.Category;
import org.example.model.Committee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommitteeRepo extends JpaRepository<Committee, UUID> {
    Optional<Committee> findByCategoryAndNumberAndYear(Category category, int number, int year);
    @Nonnull
    Optional<Committee> findById(@Nonnull UUID id);
    Optional<List<Committee>> findByYear(int year);

    @Override
    boolean existsById(@Nonnull UUID uuid);
}