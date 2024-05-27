package com.bezkoder.spring.jpa.h2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.Set;


@Entity
public class Foundation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(nullable = false, unique = true)
    private String email;


    @OneToMany(mappedBy = "foundation")
    @JsonIgnore
    private Set<EmailData> emailData;

    public Set<EmailData> getEmailData() {
        return emailData;
    }

    public void setEmailData(Set<EmailData> emailData) {
        this.emailData = emailData;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

