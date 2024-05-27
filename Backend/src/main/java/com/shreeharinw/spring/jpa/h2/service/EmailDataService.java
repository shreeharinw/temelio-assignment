package com.shreeharinw.spring.jpa.h2.service;

import com.shreeharinw.spring.jpa.h2.model.EmailData;
import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import com.shreeharinw.spring.jpa.h2.repository.EmailDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailDataService  {

    private final EmailDataRepository emailDataRepository;
    private final NonProfitService nonProfitService;

    @Autowired
    public EmailDataService(EmailDataRepository emailDataRepository, NonProfitService nonProfitService) {
        this.emailDataRepository = emailDataRepository;
        this.nonProfitService = nonProfitService;
    }

    public List<EmailData> getEmailDataByFoundationId(Long foundationId) {
        return emailDataRepository.findByFoundationId(foundationId);
    }

    public List<EmailData> getEmailDataByNonProfitId(Long nonProfitId) {
        return emailDataRepository.findByNonProfitsId(nonProfitId);
    }
    
    public EmailData saveEmailData(EmailData emailData) {
        for(NonProfit nonProfit: emailData.getNonProfits()){
            NonProfit nonProfitObj = nonProfitService.getNonProfitById(nonProfit.getId()).get();
            System.out.println("** Sending email **");
            System.out.println("Email body:");
            System.out.println("Sending money to nonprofit " + nonProfitObj.getName() + " at address " + nonProfitObj.getAddress());
            System.out.println("** Email sent successfully! **");
        }

        return emailDataRepository.save(emailData);
    }

    
    public List<EmailData> getAllEmailData() {
        return emailDataRepository.findAll();
    }

    
    public Optional<EmailData> getEmailDataById(Long id) {
        return emailDataRepository.findById(id);
    }

    
    public EmailData updateEmailData(Long id, EmailData emailData) {
        return emailDataRepository.findById(id).map(existingEmailData -> {
            existingEmailData.setMessage(emailData.getMessage());
            existingEmailData.setFoundation(emailData.getFoundation());
            existingEmailData.setNonProfits(emailData.getNonProfits());
            return emailDataRepository.save(existingEmailData);
        }).orElseThrow(() -> new RuntimeException("EmailData not found with id " + id));
    }

    
    public void deleteEmailData(Long id) {
        emailDataRepository.deleteById(id);
    }
}
