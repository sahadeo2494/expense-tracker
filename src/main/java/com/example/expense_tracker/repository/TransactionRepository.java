package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t from Transaction t WHERE t.transactionAt BETWEEN :from AND :to AND t.account.id = :accountId AND t.category.id = :categoryId AND t.type = :type")
    Page<Transaction> findByFilters(@Param("from") OffsetDateTime from,
                                    @Param("to") OffsetDateTime to,
                                    @Param("accountId") Long accountId,
                                    @Param("categoryId") Long categoryId,
                                    @Param("type") Category.TransactionType type,
                                    Pageable pageable);
}
