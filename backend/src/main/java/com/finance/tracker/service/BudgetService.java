package com.finance.tracker.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance.tracker.model.Budget;
import com.finance.tracker.repository.BudgetRepository;
import com.finance.tracker.repository.TransactionRepository;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepo;

    @Autowired
    private TransactionRepository txRepo;

    public List<Budget> getAll() {
        return budgetRepo.findAll();
    }

    public Budget save(Budget budget) {
        // If a budget for this category already exists, update its limit
        Optional<Budget> existing = budgetRepo.findByCategory(budget.getCategory());
        if (existing.isPresent()) {
            Budget toUpdate = existing.get();
            toUpdate.setMonthlyLimit(budget.getMonthlyLimit());
            return budgetRepo.save(toUpdate);
        }
        return budgetRepo.save(budget);
    }

    public boolean delete(Long id) {
        if (budgetRepo.existsById(id)) {
            budgetRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> getBudgetProgress() {
        YearMonth currentMonth = YearMonth.now();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = currentMonth.atEndOfMonth();

        List<Budget> budgets = budgetRepo.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Budget b : budgets) {
            BigDecimal spent = txRepo.sumExpenseByCategory(b.getCategory(), start, end);
            if (spent == null) spent = BigDecimal.ZERO;

            Map<String, Object> entry = new HashMap<>();
            entry.put("id", b.getId());
            entry.put("category", b.getCategory());
            entry.put("limit", b.getMonthlyLimit());
            entry.put("spent", spent);
            double pct = spent.divide(b.getMonthlyLimit(), 4, java.math.RoundingMode.HALF_UP)
                              .multiply(BigDecimal.valueOf(100))
                              .doubleValue();
            entry.put("percentage", Math.min(pct, 100));
            entry.put("remaining", b.getMonthlyLimit().subtract(spent).max(BigDecimal.ZERO));
            result.add(entry);
        }
        return result;
    }
}