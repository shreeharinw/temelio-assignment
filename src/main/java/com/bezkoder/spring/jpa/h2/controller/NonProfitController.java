package com.bezkoder.spring.jpa.h2.controller;
import com.bezkoder.spring.jpa.h2.model.NonProfit;
import com.bezkoder.spring.jpa.h2.service.NonProfitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/nonprofits")
public class NonProfitController {

    private final NonProfitService nonProfitService;

    @Autowired
    public NonProfitController(NonProfitService nonProfitService) {
        this.nonProfitService = nonProfitService;
    }

    @PostMapping
    public ResponseEntity<NonProfit> createNonProfit(@RequestBody NonProfit nonProfit) {
        NonProfit savedNonProfit = nonProfitService.saveNonProfit(nonProfit);
        return ResponseEntity.ok(savedNonProfit);
    }

    @GetMapping
    public ResponseEntity<List<NonProfit>> getAllNonProfits() {
        List<NonProfit> nonProfits = nonProfitService.getAllNonProfits();
        return ResponseEntity.ok(nonProfits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NonProfit> getNonProfitById(@PathVariable Long id) {
        Optional<NonProfit> nonProfit = nonProfitService.getNonProfitById(id);
        return nonProfit.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<NonProfit> updateNonProfit(@PathVariable Long id, @RequestBody NonProfit nonProfit) {
        try {
            NonProfit updatedNonProfit = nonProfitService.updateNonProfit(id, nonProfit);
            return ResponseEntity.ok(updatedNonProfit);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNonProfit(@PathVariable Long id) {
        try {
            nonProfitService.deleteNonProfit(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
