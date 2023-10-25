package com.nibss.dot_nibss_simulation_task.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestTransactionDto {
    private TransactionStatus status;
    private String id;
    private LocalDate dateCreated;
    private Double amount;
}
