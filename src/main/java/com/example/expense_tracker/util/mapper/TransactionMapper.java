package com.example.expense_tracker.util.mapper;

import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());

        //Set User details
        transactionDTO.setUsername(transaction.getUser().getUsername());
        transactionDTO.setEmail(transaction.getUser().getEmail());

        //Set Account details
        transactionDTO.setAccountName(transaction.getAccount().getName());

        //Set Account Details
        transactionDTO.setCategoryName(transaction.getCategory().getName());
        transactionDTO.setCategoryType(transaction.getCategory().getType());

        // Set Transaction datails
        transactionDTO.setTransactionType(transaction.getType());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setDescription(transaction.getDescription());
        transactionDTO.setLinkedTxnId(transaction.getLinkedTxnId());
        transactionDTO.setTransactionAt(transaction.getTransactionAt());
        return transactionDTO;
    }

    public Transaction toEntiry(TransactionDTO transactionDTO){
        Transaction transaction = new Transaction();
        transaction.setType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setLinkedTxnId(transactionDTO.getLinkedTxnId());
        transaction.setTransactionAt(transactionDTO.getTransactionAt());
        return transaction;
    }

}
