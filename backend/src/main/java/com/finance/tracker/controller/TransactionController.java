package com.finance.tracker.controller;

import com.finance.tracker.model.Transaction;
import com.finance.tracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public List<Transaction> getAll(@RequestParam(required = false) String type) {
        if (type != null && !type.isEmpty()) {
            return service.getByType(type);
        }
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getById(@PathVariable Long id) {
        return service.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction) {
        return ResponseEntity.ok(service.create(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @Valid @RequestBody Transaction transaction) {
        return service.update(id, transaction)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        return service.getDashboardStats();
    }
}
