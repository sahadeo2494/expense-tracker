package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.dto.AccountDTO;
import com.example.expense_tracker.dto.CategoryDTO;
import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.entity.Account;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.Transaction;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.service.TransactionService;
import com.example.expense_tracker.util.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserServiceImpl userService;
    private final AccountServiceImpl accountService;
    private final CategoryServiceImpl categoryService;

    @Override
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found: " + id));
        return transactionMapper.toDTO(transaction);
    }

    @Override
    public Page<TransactionDTO> getTransactionsByFilters(String from, String to, Long accountId, Long categoryId, String type, int page, int size) {
        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        Category.TransactionType type1 = Category.TransactionType.valueOf(type);
        AccountDTO accountDTO = accountService.getAccountById(accountId);
        CategoryDTO categoryDTO = categoryService.getCategoryById((categoryId));

        OffsetDateTime fromDateTime = fromDate.atStartOfDay().atOffset(ZoneOffset.of("+05:30"));
        OffsetDateTime toDateTime = toDate.atStartOfDay().atOffset(ZoneOffset.of("+05:30"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionsPage = transactionRepository.findByFilters(fromDateTime, toDateTime,
                                                                                    accountDTO.getId(),
                                                                                    categoryDTO.getId(), type1, pageable);

        return transactionsPage.map(transactionMapper::toDTO);
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        // Get User for User_id
        User user = userService.getUserByEmailAndUsername(transactionDTO.getEmail(), transactionDTO.getUsername());
        // Get Account from User and Account_name for account_id
        Account account = accountService.getAccountIdByUserIdAndAccountName(user.getId(), transactionDTO.getAccountName());
        Category category = categoryService.getCategoryIdByUserIdAndCategoryName(user.getId(), transactionDTO.getCategoryName());

        Transaction transaction = transactionMapper.toEntiry(transactionDTO);
        transaction.setUser(user);
        transaction.setAccount(account);
        transaction.setCategory(category);

        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    @Override
    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {

        // Check username and email against user
        User user = userService.getUserByEmailAndUsername(transactionDTO.getEmail(), transactionDTO.getUsername());

        // Get Transaction
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow( () -> new  ResourceNotFoundException("Transaction not found: " + id));

        // Validate Ownership/ Authorization
        if (!transaction.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("You cannot update someone else's transaction");
        }

        // Get
        Account account = accountService.getAccountIdByUserIdAndAccountName(user.getId(), transactionDTO.getAccountName());
        Category category = categoryService.getCategoryIdByUserIdAndCategoryName(user.getId(), transactionDTO.getCategoryName());

        transaction.setCategory(category);
        transaction.setAccount(account);
        transaction.setType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setLinkedTxnId(transactionDTO.getLinkedTxnId());
        transaction.setTransactionAt(transactionDTO.getTransactionAt());

        Transaction updated = transactionRepository.save(transaction);

        return transactionMapper.toDTO(updated);
    }

    @Override
    public void deleteTransaction(Long id) {
        if(!transactionRepository.existsById(id)) throw new ResourceNotFoundException("Transaction not found: " + id);

        transactionRepository.deleteById(id);
    }
}
