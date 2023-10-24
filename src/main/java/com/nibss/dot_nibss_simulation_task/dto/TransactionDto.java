package com.nibss.dot_nibss_simulation_task.dto;

import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private String transactionReference;
    private double amount;
    private double transactionFee;
    private double billedAmount;
    private String description;
    private LocalDateTime dateCreated;
    private TransactionStatus status;
    private boolean commissionWorthy;
    private double commission;
}
