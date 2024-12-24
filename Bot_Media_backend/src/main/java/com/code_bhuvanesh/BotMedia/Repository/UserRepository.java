package com.code_bhuvanesh.BotMedia.Repository;

import com.code_bhuvanesh.BotMedia.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods (if needed)
    Optional<User> findByUsername(String username);
}
