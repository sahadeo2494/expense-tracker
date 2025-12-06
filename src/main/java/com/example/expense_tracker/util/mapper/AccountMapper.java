package com.example.expense_tracker.util.mapper;

import com.example.expense_tracker.dto.AccountDTO;
import com.example.expense_tracker.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDTO toDTO(Account account){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setEmail(account.getUser().getEmail());
        accountDTO.setUsername(account.getUser().getUsername());
        return accountDTO;
    }

    public Account toEntity(AccountDTO accountDTO){
        Account account = new Account();
        account.setName(accountDTO.getName());
        return account;
    }

}
