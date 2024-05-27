package com.shreeharinw.spring.jpa.h2.repository;

import com.shreeharinw.spring.jpa.h2.model.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    List<EmailData> findByFoundationId(Long foundationId);

    List<EmailData> findByNonProfitsId(Long nonProfitId);
}

