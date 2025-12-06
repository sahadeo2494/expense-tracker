package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.AccountDTO;
import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.User;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    Account getAccountIdByUserIdAndAccountName(Long userId, String accountName);
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
    void deleteAccount(Long id);
}
