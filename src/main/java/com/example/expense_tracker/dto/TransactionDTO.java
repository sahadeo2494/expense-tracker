package com.example.expense_tracker.dto;

import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDTO {

    private Long id;

    // User details
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    // Account Details
    @NotBlank(message = "Account Name is required")
    private String accountName;

    // Category Details
    @NotBlank(message = "Category_Name is required")
    private String categoryName;

    @NotNull(message = "Category Type is required")
    @Enumerated(EnumType.STRING)
    private Category.TransactionType categoryType;


    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Transaction Type is required")
    @Enumerated(EnumType.STRING)
    private Category.TransactionType transactionType;

    private String description;

    @NotNull(message = "TransactionAt is required")
    private OffsetDateTime transactionAt;

    // optional field for linking related transfer transactions
    private Long linkedTxnId;
}
