package com.nibss.dot_nibss_simulation_task.service.impl;

import com.nibss.dot_nibss_simulation_task.service.MockAccountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MockAccountServiceImpl implements MockAccountService {
    private final Map<String, Double> accountBalances = new HashMap<>();

    public MockAccountServiceImpl(){
        accountBalances.put("1234567890", 1000.0);
        accountBalances.put("9876543210", 500.0);
    }

    @Override
    public double getAccountBalance(String accountNumber) {
        return accountBalances.getOrDefault(accountNumber, 0.0);
    }

    @Override
    public void updateBalance(String accountNumber, double amount) {
        double currentBalance = accountBalances.getOrDefault(accountNumber, 0.0);
        accountBalances.put(accountNumber, currentBalance + amount);
    }

    @Override
    public boolean validateAccountNumber(List<String> accountNumber) {
        List<String> accountNumbers = new ArrayList<>(accountNumber);
        return accountNumbers.stream()
                .allMatch(accountBalances::containsKey);
    }
}
