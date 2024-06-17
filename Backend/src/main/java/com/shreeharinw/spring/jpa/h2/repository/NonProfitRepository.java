package com.shreeharinw.spring.jpa.h2.repository;

import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NonProfitRepository extends JpaRepository<NonProfit, Long> {

    List<NonProfit> findByIdIn(List<Long> nonProfitIds);
    Optional<NonProfit> findByName(String name);
}

