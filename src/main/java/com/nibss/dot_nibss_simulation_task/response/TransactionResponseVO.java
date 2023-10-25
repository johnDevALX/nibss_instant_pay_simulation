package com.nibss.dot_nibss_simulation_task.response;

import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseVO {
    private Double amount;
    private String description;
    private TransactionStatus status;
    private String message;
}
