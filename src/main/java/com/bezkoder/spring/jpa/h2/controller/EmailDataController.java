package com.bezkoder.spring.jpa.h2.controller;

import com.bezkoder.spring.jpa.h2.model.EmailData;
import com.bezkoder.spring.jpa.h2.service.EmailDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/emaildata")
public class EmailDataController {

    private final EmailDataService emailDataService;

    @Autowired
    public EmailDataController(EmailDataService emailDataService) {
        this.emailDataService = emailDataService;
    }

    @PostMapping
    public ResponseEntity<EmailData> createEmailData(@RequestBody EmailData emailData) {
        EmailData savedEmailData = emailDataService.saveEmailData(emailData);
        return ResponseEntity.ok(savedEmailData);
    }

    @GetMapping
    public ResponseEntity<List<EmailData>> getAllEmailData() {
        List<EmailData> emailDataList = emailDataService.getAllEmailData();
        return ResponseEntity.ok(emailDataList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailData> getEmailDataById(@PathVariable Long id) {
        Optional<EmailData> emailData = emailDataService.getEmailDataById(id);
        return emailData.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailData> updateEmailData(@PathVariable Long id, @RequestBody EmailData emailData) {
        try {
            EmailData updatedEmailData = emailDataService.updateEmailData(id, emailData);
            return ResponseEntity.ok(updatedEmailData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailData(@PathVariable Long id) {
        try {
            emailDataService.deleteEmailData(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get EmailData by Foundation ID
    @GetMapping("/foundation/{foundationId}")
    public ResponseEntity<List<EmailData>> getEmailDataByFoundationId(@PathVariable Long foundationId) {
        List<EmailData> emailDataList = emailDataService.getEmailDataByFoundationId(foundationId);
        return ResponseEntity.ok(emailDataList);
    }

    // Get EmailData by NonProfit ID
    @GetMapping("/nonprofit/{nonProfitId}")
    public ResponseEntity<List<EmailData>> getEmailDataByNonProfitId(@PathVariable Long nonProfitId) {
        List<EmailData> emailDataList = emailDataService.getEmailDataByNonProfitId(nonProfitId);
        return ResponseEntity.ok(emailDataList);
    }
}
