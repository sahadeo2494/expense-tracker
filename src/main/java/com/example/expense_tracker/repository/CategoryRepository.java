package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query("SELECT c.id FROM Categories WHERE c.user_id = :usereId AND c.name = :categoryName")
//    Optional<Long> findByUserIdAndNameIgnoreCase(@Param("userId") Long userId,
//                                                 @Param("categoryName") String categoryName);

    Optional<Category> findByUserIdAndNameIgnoreCase(Long userId, String categoryName);

}
