package com.example.expense_tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Setter
@Getter             // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // generates no-args constructor
@AllArgsConstructor // generates all-args constructor--
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Email(message = "Email should be valid")
    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "password_hash",nullable = false, length = 255)
    private String passwordHash;

    @NotBlank(message = "Name is required")
    @Column(name = "full_name", length = 255)
    private String fullName;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
