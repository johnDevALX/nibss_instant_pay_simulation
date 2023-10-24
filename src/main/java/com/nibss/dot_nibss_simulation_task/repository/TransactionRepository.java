package com.nibss.dot_nibss_simulation_task.repository;

import com.nibss.dot_nibss_simulation_task.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
