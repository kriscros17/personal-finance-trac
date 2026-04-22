package com.finance.tracker.controller;

import com.finance.tracker.model.Budget;
import com.finance.tracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budgets")
@CrossOrigin(origins = "*")
public class BudgetController {

    @Autowired
    private BudgetService service;

    @GetMapping
    public List<Budget> getAll() {
        return service.getAll();
    }

    @PostMapping
    public ResponseEntity<Budget> save(@Valid @RequestBody Budget budget) {
        return ResponseEntity.ok(service.save(budget));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/progress")
    public List<Map<String, Object>> getProgress() {
        return service.getBudgetProgress();
    }
}
