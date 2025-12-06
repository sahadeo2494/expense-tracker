package com.example.expense_tracker.dto;

import com.example.expense_tracker.entity.Category;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionFiltersReq {

    // filters: from, to, accountId, categoryId, type, page, size

    private Date from;
    private Date to;
    private Long accountId;
    private Long categoryId;
    private Category.TransactionType type;
    private int page;
    private int size;
}
