// UserRepository.java
package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByName(String name);
    // save(), findById(), findAll() etc. are already provided for free
}