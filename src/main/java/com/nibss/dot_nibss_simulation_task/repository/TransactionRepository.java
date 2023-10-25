package com.nibss.dot_nibss_simulation_task.repository;

import com.nibss.dot_nibss_simulation_task.enums.TransactionStatus;
import com.nibss.dot_nibss_simulation_task.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByStatusAndCommissionWorthy (TransactionStatus status, boolean commissionWorthy);
    List<Transaction> findByDateCreated (LocalDate date);

    @Query("SELECT t FROM Transaction t " +
            "WHERE (:status IS NULL OR t.status = :status) " +
            "AND (:id IS NULL OR t.id = :id) " +
            "AND (:dateCreated IS NULL OR t.dateCreated = :dateCreated) " +
            "AND (:amount IS NULL OR t.amount = :amount)")
    List<Transaction> findByFilterCriteria(@Param("status") TransactionStatus status,
                                           @Param("id") String id,
                                           @Param("dateCreated") LocalDate dateCreated,
                                           @Param("amount") Double amount);
}
