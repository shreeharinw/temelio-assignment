package com.shreeharinw.spring.jpa.h2.service;

import com.shreeharinw.spring.jpa.h2.model.Foundation;
import com.shreeharinw.spring.jpa.h2.model.GrantSubmission;
import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import com.shreeharinw.spring.jpa.h2.repository.FoundationRepository;
import com.shreeharinw.spring.jpa.h2.repository.GrantSubmissionRepository;
import com.shreeharinw.spring.jpa.h2.repository.NonProfitRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class GrantSubmissionService {

    private final GrantSubmissionRepository grantSubmissionRepository;


    private final NonProfitRepository nonProfitRepository;

    private final FoundationRepository foundationRepository;

    @Autowired
    public GrantSubmissionService(GrantSubmissionRepository grantSubmissionRepository,NonProfitRepository nonProfitRepository,FoundationRepository foundationRepository ){
        this.foundationRepository=foundationRepository;
        this.nonProfitRepository=nonProfitRepository;
        this.grantSubmissionRepository=grantSubmissionRepository;
    }

    public String uploadCSVFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "Please upload a CSV file.";
        }

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            for (CSVRecord csvRecord : csvRecords) {
                String nonprofitName = csvRecord.get("Nonprofit Legal Name");
                String grantSubmissionName = csvRecord.get("Grant Submission Name");
                String stage = csvRecord.get("Stage");
                String foundationEmail = csvRecord.get("Foundation Owner");
                BigDecimal requestedAmount = parseCurrency(csvRecord.get("Requested Amount")); //new BigDecimal(csvRecord.get("Requested Amount").replace("$", "").replace(",", ""));
                BigDecimal awardedAmount = parseCurrency(csvRecord.get("Awarded Amount"));//.replace("$", "").replace(",", ""));
                GrantSubmission.GrantType grantType = GrantSubmission.GrantType.valueOf(csvRecord.get("Grant Type"));
                String tagsStr = csvRecord.get("Tags");
                List<String> tags = List.of(tagsStr.split(","));
                LocalDate durationStart = LocalDate.parse(csvRecord.get("Duration Start"), formatter);
                LocalDate durationEnd = LocalDate.parse(csvRecord.get("Duration End"), formatter);

                Optional<NonProfit> nonProfitOpt = nonProfitRepository.findByName(nonprofitName);
                Optional<Foundation> foundationOpt = foundationRepository.findByEmail(foundationEmail);

                if (nonProfitOpt.isPresent() && foundationOpt.isPresent()) {
                    GrantSubmission grantSubmission = new GrantSubmission();
                    grantSubmission.setGrantSubmissionName(grantSubmissionName);
                    grantSubmission.setStage(stage);
                    grantSubmission.setFoundationOwner(foundationOpt.get());
                    grantSubmission.setRequestedAmount(requestedAmount);
                    grantSubmission.setAwardedAmount(awardedAmount);
                    grantSubmission.setGrantType(grantType);
                    grantSubmission.setTags(tags);
                    grantSubmission.setDurationStart(durationStart);
                    grantSubmission.setDurationEnd(durationEnd);
                    grantSubmission.setNonProfit(nonProfitOpt.get());

                    grantSubmissionRepository.save(grantSubmission);
                } else {
                    // Handle missing NonProfit or Foundation
                    return "NonProfit or Foundation not found for record: " + csvRecord;
                }
            }

            return "CSV file uploaded successfully.";

        } catch (IOException e) {
            return "Failed to parse CSV file: " + e.getMessage();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private BigDecimal parseCurrency(String amountStr) throws ParseException {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        Number number = format.parse(amountStr);
        return BigDecimal.valueOf(number.doubleValue());
    }

    public List<GrantSubmission> getAllGrantSubmissions() {
        return grantSubmissionRepository.findAll();
    }
}
