package com.example.expense_tracker.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter             // generates getters, setters, toString, equals, hashCode
@NoArgsConstructor  // generates no-args constructor
@AllArgsConstructor // generates all-args constructor--
@Builder
public class AccountDTO {

    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Name is required")
    private String name;
}
