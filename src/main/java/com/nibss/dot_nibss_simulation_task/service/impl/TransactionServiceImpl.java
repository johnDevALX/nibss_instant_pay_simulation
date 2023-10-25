package com.nibss.dot_nibss_simulation_task.service.impl;

import com.nibss.dot_nibss_simulation_task.dto.TransactionDto;
import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import com.nibss.dot_nibss_simulation_task.model.Transaction;
import com.nibss.dot_nibss_simulation_task.repository.TransactionRepository;
import com.nibss.dot_nibss_simulation_task.response.TransactionResponseVO;
import com.nibss.dot_nibss_simulation_task.service.MockAccountService;
import com.nibss.dot_nibss_simulation_task.service.TransactionService;
import com.nibss.dot_nibss_simulation_task.util.HelperMethods;
import com.nibss.dot_nibss_simulation_task.util.RequestTransactionDto;
import com.nibss.dot_nibss_simulation_task.util.TransactionSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final HelperMethods helperMethods;
    private final TransactionRepository transactionRepository;
    private final MockAccountService mockAccountService;


    @Override
    public TransactionResponseVO processTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        List<String> accountNumbers = new ArrayList<>();
        accountNumbers.add(transactionDto.getSenderAccount());
        accountNumbers.add(transactionDto.getReceiverAccount());

        if (transactionDto.getSenderAccount().equals(transactionDto.getReceiverAccount())) {
            return transaction.returnTransactionResponse("Sender and Receiver account numbers match. Please provide different unique account numbers.");
        }
        if (!mockAccountService.validateAccountNumber(accountNumbers)) {
            Transaction transaction1 = transactionRepository.save(helperMethods.mapToFailedTransactionObj(transactionDto));
            return transaction1.returnTransactionResponse(transaction1, "Invalid account number provided. Please provide a valid account number and try again.");
        }
        if (!helperMethods.hasSufficientFunds(transactionDto.getSenderAccount(), transactionDto.getAmount())) {
            Transaction transaction1 = transactionRepository.save(helperMethods.mapToFailedTransactionObj(transactionDto));
            return transaction1.returnTransactionResponse(transaction1, "Sender account has insufficient balance.");
        } else {
            Transaction transaction1 = transactionRepository.save(helperMethods.mapToTransactionObj(transactionDto));
            helperMethods.updateAccountBalances(transactionDto.getSenderAccount(), transactionDto.getReceiverAccount(), transactionDto.getAmount());
            return transaction1.returnTransactionResponse(transaction1, "Transaction successful");
        }
    }

    @Override
    public List<Transaction> getTransactions(RequestTransactionDto requestTransactionDto) {
        List<Transaction> transactions;
        if (Objects.isNull(requestTransactionDto)) {
            transactions = transactionRepository.findAll();
        } else {
            transactions = transactionRepository.findByFilterCriteria(requestTransactionDto.getStatus(),
                    requestTransactionDto.getId(),
                    requestTransactionDto.getDateCreated(),
                    requestTransactionDto.getAmount());
        }
        return transactions;
    }

    @Override
    public TransactionSummary generateSummary(LocalDate date) {
        List<Transaction> transactions = transactionRepository.findByDateCreated(date);
        double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        double totalCommission = transactions.stream().mapToDouble(Transaction::getCommission).sum();

        TransactionSummary summary = new TransactionSummary();
        summary.setDate(date);
        summary.setTotalAmount(totalAmount);
        summary.setTotalCommission(totalCommission);
        return summary;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void calculateCommissionsForSuccessfulTransactions() {
        List<Transaction> successfulTransactions = transactionRepository
                .findByStatusAndCommissionWorthy(TransactionStatus.SUCCESSFUL, false);
        successfulTransactions.forEach(transaction -> {
            double transactionFee = transaction.getTransactionFee();
            double commission = helperMethods.calculateCommission(transactionFee);

            transaction.setCommission(commission);
            transaction.setCommissionWorthy(true);
        });
        transactionRepository.saveAll(successfulTransactions);
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void generateDailySummary() {
        LocalDate currentDate = LocalDate.now();
        TransactionSummary summary = generateSummary(currentDate);
        log.info("Summary generated  {}", summary);
    }
}
