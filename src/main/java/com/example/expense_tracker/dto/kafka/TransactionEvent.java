package com.example.expense_tracker.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {

    private Long id;
    private Long accountId;
    private Double amount;
    private String type;

    @Override
    public String toString(){
       return "TransactionEvent{" +
               "id=" + id +
               ", accountId= " + accountId +
               ", amount=" + amount +
               ", type=" + type + '\'' +
               '}';
    }
}
