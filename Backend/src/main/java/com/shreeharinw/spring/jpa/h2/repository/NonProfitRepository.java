package com.shreeharinw.spring.jpa.h2.repository;

import com.shreeharinw.spring.jpa.h2.model.NonProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NonProfitRepository extends JpaRepository<NonProfit, Long> {

    List<NonProfit> findByIdIn(List<Long> nonProfitIds);
}

