package com.nibss.dot_nibss_simulation_task.model;

import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import com.nibss.dot_nibss_simulation_task.response.TransactionResponseVO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String senderAccount;
    private String receiverAccount;
    private String transactionReference;
    private Double amount;
    private Double transactionFee;
    private Double billedAmount;
    private String description;
    private LocalDate dateCreated;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private boolean commissionWorthy;
    private Double commission;


    public TransactionResponseVO returnTransactionResponse(Transaction transaction, String message){
        return TransactionResponseVO.builder()
                .description(transaction.getDescription())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .message(message)
                .build();
    }

    public TransactionResponseVO returnTransactionResponse(String message){
        return TransactionResponseVO.builder()
                .description(null)
                .amount(null)
                .status(TransactionStatus.FORBIDDEN)
                .message(message)
                .build();
    }
}
