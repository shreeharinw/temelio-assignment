package com.shreeharinw.spring.jpa.h2.repository;

import com.shreeharinw.spring.jpa.h2.model.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoundationRepository extends JpaRepository<Foundation, Long> {

    Optional<Foundation> findByEmail(String email);
}

