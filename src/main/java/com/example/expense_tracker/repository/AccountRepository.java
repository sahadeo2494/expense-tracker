package com.example.expense_tracker.repository;

import com.example.expense_tracker.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//     Query = Select * from Account where user_id = ? and name = ?;
    Optional<Account> findByUserIdAndNameIgnoreCase(Long userId, String name);

//    @Query("SELECT a.id FROM Accounts WHERE a.user_id = :userId AND a.name = :accountName" )
//    Optional<Long> findByUserIdAndNameIgnoreCase(@Param("userId") Long userId,
//                                                 @Param("accountName") String accountName);
    Boolean existsByIdAndUserId(Long accountId, Long userId);


}
