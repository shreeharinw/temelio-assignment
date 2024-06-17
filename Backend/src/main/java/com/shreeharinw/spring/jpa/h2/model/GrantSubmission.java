package com.shreeharinw.spring.jpa.h2.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "grant_submissions")
public class GrantSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grant_submission_name", nullable = false)
    private String grantSubmissionName;

    @Column(name = "stage", nullable = false)
    private String stage;

    @ManyToOne
    @JoinColumn(name = "foundation_owner_id", nullable = false)
    private Foundation foundationOwner;

    @Column(name = "requested_amount", nullable = false)
    private BigDecimal requestedAmount;

    @Column(name = "awarded_amount", nullable = true)
    private BigDecimal awardedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GrantType grantType;

    @ElementCollection
    private List<String> tags;

    @Column(name = "duration_start", nullable = false)
    private LocalDate durationStart;

    @Column(name = "duration_end", nullable = false)
    private LocalDate durationEnd;

    @Column(name = "additional_file_folder_path", nullable = true)
    private String additionalFileFolderPath;

    @Column(name = "grant_submission_id", nullable = true)
    private String grantSubmissionId;

    @ManyToOne
    @JoinColumn(name = "nonprofit_id", nullable = false)
    private NonProfit nonProfit;

    public GrantType getGrantType() {
        return grantType;
    }

    public void setGrantType(GrantType grantType) {
        this.grantType = grantType;
    }

    public GrantSubmission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrantSubmissionName() {
        return grantSubmissionName;
    }

    public void setGrantSubmissionName(String grantSubmissionName) {
        this.grantSubmissionName = grantSubmissionName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Foundation getFoundationOwner() {
        return foundationOwner;
    }

    public void setFoundationOwner(Foundation foundationOwner) {
        this.foundationOwner = foundationOwner;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public BigDecimal getAwardedAmount() {
        return awardedAmount;
    }

    public void setAwardedAmount(BigDecimal awardedAmount) {
        this.awardedAmount = awardedAmount;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDate getDurationStart() {
        return durationStart;
    }

    public void setDurationStart(LocalDate durationStart) {
        this.durationStart = durationStart;
    }

    public LocalDate getDurationEnd() {
        return durationEnd;
    }

    public void setDurationEnd(LocalDate durationEnd) {
        this.durationEnd = durationEnd;
    }

    public String getAdditionalFileFolderPath() {
        return additionalFileFolderPath;
    }

    public void setAdditionalFileFolderPath(String additionalFileFolderPath) {
        this.additionalFileFolderPath = additionalFileFolderPath;
    }

    public String getGrantSubmissionId() {
        return grantSubmissionId;
    }

    public void setGrantSubmissionId(String grantSubmissionId) {
        this.grantSubmissionId = grantSubmissionId;
    }

    public NonProfit getNonProfit() {
        return nonProfit;
    }

    public void setNonProfit(NonProfit nonProfit) {
        this.nonProfit = nonProfit;
    }

    public enum GrantType {
        OPERATING_GRANT,
        PROJECT_GRANT
    }
}

