package com.shreeharinw.spring.jpa.h2.controller;

import com.shreeharinw.spring.jpa.h2.model.GrantSubmission;
import com.shreeharinw.spring.jpa.h2.service.GrantSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/grantsubmissions")
public class GrantSubmissionController {

    @Autowired
    private GrantSubmissionService grantSubmissionService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        try{
            String response = grantSubmissionService.uploadCSVFile(file);
            if (response.startsWith("Failed") || response.startsWith("NonProfit or Foundation not found")) {
                return ResponseEntity.status(500).body(response);
            }
            return ResponseEntity.ok(response);
        }catch(Error e){
            return ResponseEntity.status(500).body("failed");
        }
    }

    @GetMapping
    public ResponseEntity<List<GrantSubmission>> getAllGrantSubmissions() {
        List<GrantSubmission> grants = grantSubmissionService.getAllGrantSubmissions();
        return ResponseEntity.ok(grants);
    }

}

