package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.kafka.TransactionEvent;
import com.example.expense_tracker.entity.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @KafkaListener(topics = "transactions", groupId = "transaction-service-group")
    public void consume(TransactionEvent event){
        System.out.println("Received transaction event: " + event);
    }
}
