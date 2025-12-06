package com.example.expense_tracker.service;

import com.example.expense_tracker.dto.kafka.TransactionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionProducer {

    private static final String TOPIC = "transactions";

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void send(TransactionEvent event){
        String key = String.valueOf(event.getAccountId());
        kafkaTemplate.send(TOPIC, key, event);
    }


}
