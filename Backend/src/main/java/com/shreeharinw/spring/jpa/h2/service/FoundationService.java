package com.shreeharinw.spring.jpa.h2.service;

import com.shreeharinw.spring.jpa.h2.model.Foundation;
import com.shreeharinw.spring.jpa.h2.repository.FoundationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoundationService {

    private final FoundationRepository foundationRepository;

    @Autowired
    public FoundationService(FoundationRepository foundationRepository) {
        this.foundationRepository = foundationRepository;
    }


    public Optional<Foundation> findByEmail(String email) {
        return foundationRepository.findByEmail(email);
    }

    public Foundation saveFoundation(Foundation foundation) {
        return foundationRepository.save(foundation);
    }


    public List<Foundation> getAllFoundations() {
        return foundationRepository.findAll();
    }


    public Optional<Foundation> getFoundationById(Long id) {
        return foundationRepository.findById(id);
    }


    public Foundation updateFoundation(Long id, Foundation foundation) {
        return foundationRepository.findById(id).map(existingFoundation -> {
            existingFoundation.setEmail(foundation.getEmail());
            return foundationRepository.save(existingFoundation);
        }).orElseThrow(() -> new RuntimeException("Foundation not found with id " + id));
    }

    public void deleteFoundation(Long id) {
        foundationRepository.deleteById(id);
    }
}

