package com.shreeharinw.spring.jpa.h2.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class EmailData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "foundation_id", nullable = false)
    private Foundation foundation;

    @ManyToMany
    @JoinTable(
            name = "emaildata_nonprofit",
            joinColumns = @JoinColumn(name = "emaildata_id"),
            inverseJoinColumns = @JoinColumn(name = "nonprofit_id")
    )
    private Set<NonProfit> nonProfits;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Foundation getFoundation() {
        return foundation;
    }

    public void setFoundation(Foundation foundation) {
        this.foundation = foundation;
    }

    public Set<NonProfit> getNonProfits() {
        return nonProfits;
    }

    public void setNonProfits(Set<NonProfit> nonProfits) {
        this.nonProfits = nonProfits;
    }
}

