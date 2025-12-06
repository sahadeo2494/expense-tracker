package com.example.expense_tracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "transactions"
)
@Data               // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // generates no-args constructor
@AllArgsConstructor // generates all-args constructor--
public class Transaction {

    // Recommended Hybrid (for production + JPA best practices)
    public enum TransactionType {
        INCOME, EXPENSE, TRANSFER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "fk_transaction_account"))
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_transaction_category"))
    private Category category;

    @Column(precision = 19, scale = 4, nullable = false, columnDefinition = "CHECK (amount >= 0)")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Category.TransactionType type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "transaction_at", nullable = false)
    private OffsetDateTime transactionAt;

    // optional field for linking related transfer transactions
    @Column(name = "linked_txn_id")
    private Long linkedTxnId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

}
