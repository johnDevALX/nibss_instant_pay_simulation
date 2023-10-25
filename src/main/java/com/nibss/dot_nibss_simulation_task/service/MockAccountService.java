package com.nibss.dot_nibss_simulation_task.service;

import java.util.List;

public interface MockAccountService {
    double getAccountBalance(String accountNumber);
    void updateBalance(String accountNumber, double amount);
    boolean validateAccountNumber(List<String> accountNumber);

}
