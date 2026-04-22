package com.finance.tracker.repository;

import com.finance.tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByOrderByDateDesc();

    List<Transaction> findByTypeOrderByDateDesc(Transaction.TransactionType type);

    List<Transaction> findByCategoryOrderByDateDesc(String category);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type")
    BigDecimal sumByType(@Param("type") Transaction.TransactionType type);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type AND t.date >= :startDate AND t.date <= :endDate")
    BigDecimal sumByTypeAndDateBetween(
        @Param("type") Transaction.TransactionType type,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.category = :category AND t.type = 'EXPENSE' AND t.date >= :startDate AND t.date <= :endDate")
    BigDecimal sumExpenseByCategory(
        @Param("category") String category,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.type = 'EXPENSE' GROUP BY t.category")
    List<Object[]> expensesByCategory();

    List<Transaction> findTop5ByOrderByDateDesc();
}
