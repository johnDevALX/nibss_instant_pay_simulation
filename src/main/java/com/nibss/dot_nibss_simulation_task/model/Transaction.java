package com.nibss.dot_nibss_simulation_task.model;

import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
