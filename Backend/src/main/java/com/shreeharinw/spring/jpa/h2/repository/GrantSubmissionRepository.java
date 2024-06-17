package com.shreeharinw.spring.jpa.h2.repository;

import com.shreeharinw.spring.jpa.h2.model.GrantSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantSubmissionRepository extends JpaRepository<GrantSubmission, Long> {
}

