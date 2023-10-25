package com.nibss.dot_nibss_simulation_task.util;

import com.nibss.dot_nibss_simulation_task.dto.TransactionDto;
import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import com.nibss.dot_nibss_simulation_task.model.Transaction;
import com.nibss.dot_nibss_simulation_task.service.MockAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class HelperMethods {
    private final MockAccountService mockAccountService;
    public boolean hasSufficientFunds(String senderAccount, double transferAmount) {
        double senderBalance = mockAccountService.getAccountBalance(senderAccount);
        return senderBalance >= transferAmount;
    }

    public void updateAccountBalances(String senderAccount, String receiverAccount, double amount) {
        mockAccountService.updateBalance(senderAccount, -amount);
        mockAccountService.updateBalance(receiverAccount, amount);
    }

    public String generateTransactionReference() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomPart = String.format("%04d", new Random().nextInt(10000));
        return "TXN" + timestamp + randomPart;
    }

    public double calculateTransactionFee(double transferAmount) {
        double feePercentage = 0.005;
        double maximumFee = 100;
        double fee = transferAmount * feePercentage;
        log.info("fee  -> {}", fee);
        log.info("Object  -> {}", Math.min(fee, maximumFee));
        return Math.min(fee, maximumFee);
    }

    public TransactionStatus calculateTransactionStatus(TransactionDto request) {
        List<String> accountNumbers = new ArrayList<>();
        accountNumbers.add(request.getSenderAccount());
        accountNumbers.add(request.getReceiverAccount());
        if (!mockAccountService.validateAccountNumber(accountNumbers)){
            return TransactionStatus.FAILED;
        }
        return hasSufficientFunds(request.getSenderAccount(), request.getAmount()) ? TransactionStatus.SUCCESSFUL : TransactionStatus.INSUFFICIENT_FUND;
    }

    public double calculateCommission(double transactionFee) {
        double commissionPercentage = 0.20;
        double maximumCommission = 100;
        double commission = transactionFee * commissionPercentage;
        log.info("commission  -> {}", commission);
        log.info("Object  -> {}", Math.min(commission, maximumCommission));
        return Math.min(commission, maximumCommission);
    }

    public Transaction mapToTransactionObj(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(transactionDto.getSenderAccount());
        transaction.setReceiverAccount(transactionDto.getReceiverAccount());
        transaction.setTransactionReference(generateTransactionReference());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionFee(calculateTransactionFee(transactionDto.getAmount()));
        transaction.setBilledAmount(transactionDto.getAmount() + transaction.getTransactionFee());
        transaction.setDescription(transactionDto.getDescription());
        transaction.setDateCreated(LocalDate.now());
        transaction.setStatus(calculateTransactionStatus(transactionDto));
        transaction.setCommissionWorthy(false);
        transaction.setCommission(0.0);
        return transaction;
    }

    public Transaction mapToFailedTransactionObj(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setSenderAccount(transactionDto.getSenderAccount());
        transaction.setReceiverAccount(transactionDto.getReceiverAccount());
        transaction.setTransactionReference(generateTransactionReference());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setTransactionFee(0.0);
        transaction.setBilledAmount(0.0);
        transaction.setDescription(transactionDto.getDescription());
        transaction.setDateCreated(LocalDate.now());
        transaction.setStatus(calculateTransactionStatus(transactionDto));
        transaction.setCommissionWorthy(false);
        transaction.setCommission(0.0);
        return transaction;
    }

    public TransactionDto mapToTransactionDto(Transaction transaction){
        try {
            return TransactionDto.builder()
                    .senderAccount(transaction.getSenderAccount())
                    .receiverAccount(transaction.getReceiverAccount())
                    .amount(transaction.getAmount())
                    .transactionFee(transaction.getTransactionFee())
                    .billedAmount(transaction.getBilledAmount())
                    .description(transaction.getDescription())
                    .dateCreated(transaction.getDateCreated())
                    .transactionReference(transaction.getTransactionReference())
                    .status(transaction.getStatus())
                    .commission(transaction.getCommission())
                    .commissionWorthy(transaction.isCommissionWorthy())
                    .build();
        } catch (Exception e){
            log.info("Exception occurred in mapping transaction to transactionDto");
            return null;
        }
    }

}
