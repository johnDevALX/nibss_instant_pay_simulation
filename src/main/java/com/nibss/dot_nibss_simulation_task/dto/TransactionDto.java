package com.nibss.dot_nibss_simulation_task.dto;

import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private Long id;
    private String senderAccount;
    private String receiverAccount;
    private String transactionReference;
    private Double amount;
    private Double transactionFee;
    private Double billedAmount;
    private String description;
    private LocalDate dateCreated;
    private TransactionStatus status;
    private boolean commissionWorthy;
    private Double commission;
}
