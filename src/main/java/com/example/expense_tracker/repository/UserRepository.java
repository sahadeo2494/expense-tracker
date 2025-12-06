package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean deleteByEmail(String email);

    Optional<User> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String email);
    boolean deleteByUsername(String email);

    Optional<User> findByEmailIgnoreCaseAndUsernameIgnoreCase(String email, String username);
}
