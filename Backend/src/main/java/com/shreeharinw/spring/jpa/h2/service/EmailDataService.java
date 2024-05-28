package com.shreeharinw.spring.jpa.h2.service;

import com.shreeharinw.spring.jpa.h2.model.EmailData;
import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import com.shreeharinw.spring.jpa.h2.repository.EmailDataRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmailDataService  {

    private final EmailDataRepository emailDataRepository;
    private final NonProfitService nonProfitService;

    private final EmailNotifier emailNotifier;

    @Autowired
    public EmailDataService(EmailDataRepository emailDataRepository, NonProfitService nonProfitService,
                            EmailNotifier emailNotifier) {
        this.emailDataRepository = emailDataRepository;
        this.nonProfitService = nonProfitService;
        this.emailNotifier= emailNotifier;
    }

    public List<EmailData> getEmailDataByFoundationId(Long foundationId) {
        return emailDataRepository.findByFoundationId(foundationId);
    }

    public List<EmailData> getEmailDataByNonProfitId(Long nonProfitId) {
        return emailDataRepository.findByNonProfitsId(nonProfitId);
    }
    
    public EmailData saveEmailData(EmailData emailData) {
        List<NonProfit> nonProfits = nonProfitService.getAllByIds(emailData.getNonProfits().stream().map(NonProfit::getId).collect(Collectors.toList()));
        emailNotifier.notify(nonProfits);
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
