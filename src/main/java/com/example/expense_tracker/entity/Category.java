package com.example.expense_tracker.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id","name","type"})
        }
)
@Data               // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // generates no-args constructor
@AllArgsConstructor // generates all-args constructor--
public class Category {

    // Recommended Hybrid (for production + JPA best practices)
    public enum TransactionType {
        INCOME, EXPENSE;

        @JsonCreator
        public static TransactionType fromString(String value){
            for(TransactionType type : TransactionType.values()){
                if(type.name().equalsIgnoreCase(value)){
                    return type;
                }
            }
            throw new RuntimeException("Invalid transaction type: " + value);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_category_user"))
    private User user;


    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TransactionType type;

    @Column(length = 7)
    private String color;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}
