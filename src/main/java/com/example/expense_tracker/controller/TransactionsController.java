package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.dto.kafka.TransactionEvent;
import com.example.expense_tracker.service.TransactionProducer;
import com.example.expense_tracker.service.impl.TransactionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionsController {

    private final TransactionServiceImpl transactionService;
    private final TransactionProducer producer;

//    GET /api/v1/transactions — filters: from, to, accountId, categoryId, type, page, size
    // from=2025-09-01&to=2025-10-31&accountId=1&categoryId=2&type=EXPENSE&page=0&size=10
    @GetMapping
    public Page<TransactionDTO> getTransactionsForFilters(@RequestParam String from,
                                                          @RequestParam String to,
                                                          @RequestParam Long accountId,
                                                          @RequestParam Long categoryId,
                                                          @RequestParam String type,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){

        return transactionService.getTransactionsByFilters(from, to, accountId, categoryId, type, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@Valid @PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO){
        System.out.println("Controller hit!");
        TransactionDTO created = transactionService.createTransaction(transactionDTO);
        URI location = URI.create("/api/v1/transactions/" + created.getId());
        return ResponseEntity.created(location).body(created);  // 201 + body + Location
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@Valid @PathVariable Long id,
                                            @Valid @RequestBody TransactionDTO transactionDTO){
        return ResponseEntity.ok(transactionService.updateTransaction(id ,transactionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> createTransaction(@Valid @PathVariable Long id){
        transactionService.deleteTransaction(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody TransactionEvent event){
        producer.send(event);
        return ResponseEntity.ok("Event published to Kafka");
    }

}
