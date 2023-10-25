package com.nibss.dot_nibss_simulation_task.controller;

import com.nibss.dot_nibss_simulation_task.dto.TransactionDto;
import com.nibss.dot_nibss_simulation_task.service.TransactionService;
import com.nibss.dot_nibss_simulation_task.util.RequestTransactionDto;
import lombok.RequiredArgsConstructor;
import net.ekene.utility.BaseController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class TransactionController extends BaseController {
    private final TransactionService transactionService;


    @PostMapping("pay")
    public ResponseEntity<?> acceptPayment(@RequestBody TransactionDto transactionDto){
        return getResponse(HttpStatus.CREATED, "created", transactionService.processTransaction(transactionDto));
    }

    @GetMapping("generateDailySummary/{date}")
    public ResponseEntity<?> generateDailySummary(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return getResponse(HttpStatus.ACCEPTED, "Success", transactionService.generateSummary(date));
    }

    @GetMapping("retrieveTransactions")
    public ResponseEntity<?> retrieveTransactions(@RequestBody(required = false) RequestTransactionDto requestTransactionDto){

        return getResponse(HttpStatus.OK, "Retrieved", transactionService.getTransactions(requestTransactionDto));
    }
}
