package com.shreeharinw.spring.jpa.h2.controller;
import com.shreeharinw.spring.jpa.h2.model.Foundation;
import com.shreeharinw.spring.jpa.h2.service.FoundationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/foundations")
public class FoundationController {

    private final FoundationService foundationService;

    @Autowired
    public FoundationController(FoundationService foundationService) {
        this.foundationService = foundationService;
    }

    @PostMapping
    public ResponseEntity<Foundation> createFoundation(@RequestBody Foundation foundation) {
        Foundation savedFoundation = foundationService.saveFoundation(foundation);
        return ResponseEntity.ok(savedFoundation);
    }

    @GetMapping
    public ResponseEntity<List<Foundation>> getAllFoundations() {
        List<Foundation> foundations = foundationService.getAllFoundations();
        return ResponseEntity.ok(foundations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Foundation> getFoundationById(@PathVariable Long id) {
        Optional<Foundation> foundation = foundationService.getFoundationById(id);
        return foundation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Foundation> updateFoundation(@PathVariable Long id, @RequestBody Foundation foundation) {
        try {
            Foundation updatedFoundation = foundationService.updateFoundation(id, foundation);
            return ResponseEntity.ok(updatedFoundation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoundation(@PathVariable Long id) {
        try {
            foundationService.deleteFoundation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

