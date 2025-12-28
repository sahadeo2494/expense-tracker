package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.TransactionDTO;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.service.TransactionProducer;
import com.example.expense_tracker.service.impl.TransactionServiceImpl;
import com.example.expense_tracker.util.mapper.TransactionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionsController.class) // loads only MVC stuff + this controller.
@AutoConfigureMockMvc(addFilters = false) // disable security filters for this test
public class TransactionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionServiceImpl transactionService;

    @MockitoBean
    private TransactionProducer transactionProducer;

    @Test
    void getTransactionById_shouldReturnTransactionDto() throws Exception{

        Long id = 1L;
        BigDecimal amount = new BigDecimal(10000);
        OffsetDateTime transactionAt = OffsetDateTime.parse("2025-09-01T04:30:00Z");

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .id(id)
                .username("sahadeo")
                .email("sa@gmail.com")
                .accountName("Saving")
                .categoryName("Transport")
                .categoryType(Category.TransactionType.EXPENSE)
                .amount(amount)
                .transactionType(Category.TransactionType.INCOME)
                .description("Monthly Salary")
                .transactionAt(transactionAt)
                .build();

        when(transactionService.getTransactionById(id)).thenReturn(transactionDTO);

        //when + then
        mockMvc.perform(get("/api/v1/transactions/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("sahadeo"));
    }
}
