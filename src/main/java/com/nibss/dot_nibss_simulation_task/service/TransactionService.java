package com.nibss.dot_nibss_simulation_task.service;

import com.nibss.dot_nibss_simulation_task.dto.TransactionDto;
import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import com.nibss.dot_nibss_simulation_task.model.Transaction;
import com.nibss.dot_nibss_simulation_task.response.TransactionResponseVO;
import com.nibss.dot_nibss_simulation_task.util.RequestTransactionDto;
import com.nibss.dot_nibss_simulation_task.util.TransactionSummary;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionResponseVO processTransaction(TransactionDto transactionDto);
    List<Transaction> getTransactions(RequestTransactionDto requestTransactionDto);
    TransactionSummary generateSummary(LocalDate date);
}
