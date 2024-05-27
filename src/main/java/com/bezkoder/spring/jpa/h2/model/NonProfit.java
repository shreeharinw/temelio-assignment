package com.bezkoder.spring.jpa.h2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class NonProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(nullable = false)
    private String name;

    @Nonnull
    @Column(nullable = false)
    private String address;

    @Nonnull
    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(mappedBy = "nonProfits")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

