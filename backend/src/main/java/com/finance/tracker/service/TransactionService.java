package com.finance.tracker.service;

import com.finance.tracker.model.Transaction;
import com.finance.tracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repo;

    public List<Transaction> getAll() {
        return repo.findAllByOrderByDateDesc();
    }

    public Optional<Transaction> getById(Long id) {
        return repo.findById(id);
    }

    public Transaction create(Transaction t) {
        if (t.getDate() == null) t.setDate(LocalDate.now());
        return repo.save(t);
    }

    public Optional<Transaction> update(Long id, Transaction updated) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setAmount(updated.getAmount());
            existing.setCategory(updated.getCategory());
            existing.setType(updated.getType());
            existing.setDate(updated.getDate());
            existing.setNote(updated.getNote());
            return repo.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        BigDecimal totalIncome = repo.sumByType(Transaction.TransactionType.INCOME);
        BigDecimal totalExpense = repo.sumByType(Transaction.TransactionType.EXPENSE);
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;

        stats.put("totalIncome", totalIncome);
        stats.put("totalExpense", totalExpense);
        stats.put("balance", totalIncome.subtract(totalExpense));

        // This month stats
        YearMonth currentMonth = YearMonth.now();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        BigDecimal monthIncome = repo.sumByTypeAndDateBetween(Transaction.TransactionType.INCOME, start, end);
        BigDecimal monthExpense = repo.sumByTypeAndDateBetween(Transaction.TransactionType.EXPENSE, start, end);
        if (monthIncome == null) monthIncome = BigDecimal.ZERO;
        if (monthExpense == null) monthExpense = BigDecimal.ZERO;

        stats.put("monthIncome", monthIncome);
        stats.put("monthExpense", monthExpense);

        // Expenses by category
        List<Object[]> byCategory = repo.expensesByCategory();
        Map<String, BigDecimal> categoryMap = new HashMap<>();
        for (Object[] row : byCategory) {
            categoryMap.put((String) row[0], (BigDecimal) row[1]);
        }
        stats.put("expensesByCategory", categoryMap);

        // Recent transactions
        stats.put("recentTransactions", repo.findTop5ByOrderByDateDesc());

        return stats;
    }

    public List<Transaction> getByType(String type) {
        return repo.findByTypeOrderByDateDesc(
            Transaction.TransactionType.valueOf(type.toUpperCase())
        );
    }
}
