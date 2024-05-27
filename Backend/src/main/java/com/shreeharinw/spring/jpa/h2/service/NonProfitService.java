package com.shreeharinw.spring.jpa.h2.service;
import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import com.shreeharinw.spring.jpa.h2.repository.NonProfitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NonProfitService{

    private final NonProfitRepository nonProfitRepository;

    @Autowired
    public NonProfitService(NonProfitRepository nonProfitRepository) {
        this.nonProfitRepository = nonProfitRepository;
    }

    
    public NonProfit saveNonProfit(NonProfit nonProfit) {
        return nonProfitRepository.save(nonProfit);
    }

    
    public List<NonProfit> getAllNonProfits() {
        return nonProfitRepository.findAll();
    }

    
    public Optional<NonProfit> getNonProfitById(Long id) {
        return nonProfitRepository.findById(id);
    }

    
    public NonProfit updateNonProfit(Long id, NonProfit nonProfit) {
        return nonProfitRepository.findById(id).map(existingNonProfit -> {
            existingNonProfit.setName(nonProfit.getName());
            existingNonProfit.setAddress(nonProfit.getAddress());
            existingNonProfit.setEmail(nonProfit.getEmail());
            return nonProfitRepository.save(existingNonProfit);
        }).orElseThrow(() -> new RuntimeException("NonProfit not found with id " + id));
    }

    
    public void deleteNonProfit(Long id) {
        nonProfitRepository.deleteById(id);
    }
}

