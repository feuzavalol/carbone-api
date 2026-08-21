// UserRepository.java
package org.example.repository;

import jakarta.annotation.Nonnull;
import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
    @Nonnull
    Optional<User> findById(@Nonnull UUID uuid);

    @Override
    boolean existsById(@Nonnull UUID uuid);

    Optional<User> findByEmail(String email);
}