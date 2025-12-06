package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.CategoryDTO;
import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

//    List<TransactionDTO> getTransaction(RequestBody request);
    TransactionDTO getTransactionById(Long id);
    Page<TransactionDTO> getTransactionsByFilters(String from, String to,
                                                  Long accountId, Long categoryId,
                                                  String type,
                                                  int page, int size);
    TransactionDTO createTransaction(TransactionDTO transactionDTO);
    TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);
    void deleteTransaction(Long id);

}
