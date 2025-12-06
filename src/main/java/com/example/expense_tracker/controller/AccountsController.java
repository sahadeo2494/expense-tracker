package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.AccountDTO;
import com.example.expense_tracker.service.impl.AccountServiceImpl;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountsController {

    private final AccountServiceImpl accountService;

    @GetMapping
    public List<AccountDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    // Get Account by Id
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable @Positive Long id){
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    // Create Account
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Validated AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.createAccount(accountDTO));
    }

    // Update Account
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable @Positive Long id,
                                                    @RequestBody @Validated AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.updateAccount(id, accountDTO));
    }

    // Delete Account
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable @Positive Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }


}
