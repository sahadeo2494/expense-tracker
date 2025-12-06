package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.dto.AccountDTO;
import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.repository.AccountRepository;
import com.example.expense_tracker.service.AccountService;
import com.example.expense_tracker.util.mapper.AccountMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserServiceImpl userService;

    // Get All Accounts
    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDTO> accountDTOList = new ArrayList<>();

        for (Account account : accountList){
            AccountDTO accountDTO = accountMapper.toDTO(account);
//            accountDTO.setName();
            accountDTOList.add(accountDTO);
        }

        return accountDTOList;
    }

    // Get Account By id
    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
        return accountMapper.toDTO(account);
    }

    @Override
    public Account getAccountIdByUserIdAndAccountName(Long userId, String accountName) {
        return accountRepository.findByUserIdAndNameIgnoreCase(userId, accountName)
                .orElseThrow(() -> new RuntimeException("Account not found: for UserId " + userId + " Account Name " + accountName));
    }

    // Get AccountDTO as object
    // Check username exit or email is exist
    // then bind userId to account
    // Mapping one has many accounts
    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        User user = userService.getUserByEmailAndUsername(accountDTO.getEmail(), accountDTO.getUsername());
        Account account = accountMapper.toEntity(accountDTO);
        account.setUser(user);
        return accountMapper.toDTO(accountRepository.save(account));
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {

        // Check username and email against User
        User user = userService.getUserByEmailAndUsername(accountDTO.getEmail(), accountDTO.getUsername());

        // Load account
        Account acc = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found : " + id));

        // Authorization/Onwership check
        if(!acc.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("You cannot update someone else's account");
        }
        acc.setName(accountDTO.getName());

        Account updated = accountRepository.save(acc);
        return accountMapper.toDTO(updated);
    }

    @Override
    public void deleteAccount(Long id) {
        if(!accountRepository.existsById(id))   throw new RuntimeException("Account not found: " + id) ;
        accountRepository.deleteById(id);
    }

}
