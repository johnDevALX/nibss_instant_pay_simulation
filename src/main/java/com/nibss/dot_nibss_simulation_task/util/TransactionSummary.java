package com.nibss.dot_nibss_simulation_task.util;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionSummary {
    private LocalDate date;
    private double totalAmount;
    private double totalCommission;
}
