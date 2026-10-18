package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "service_providers")
@Data

public class ServiceProvider {

    public ServiceProvider() {
    }

    public ServiceProvider(Long id, Boolean verified, String availability, Long userId, User user, List<ProviderLocation> locations, List<ProviderSkill> skills, Integer completedJobsCount, Integer rejectedJobsCount, Double hourlyRate, String providerType, Long serviceCategoryId, ServiceCategory category) {
        this.id = id;
        this.verified = verified != null ? verified : false;
        this.availability = availability != null ? availability : "AVAILABLE";
        this.userId = userId;
        this.user = user;
        this.locations = locations;
        this.skills = skills;
        this.completedJobsCount = completedJobsCount != null ? completedJobsCount : 0;
        this.rejectedJobsCount = rejectedJobsCount != null ? rejectedJobsCount : 0;
        this.hourlyRate = hourlyRate;
        this.providerType = providerType;
        this.serviceCategoryId = serviceCategoryId;
        this.category = category;
    }

    public static ServiceProviderBuilder builder() {
        return new ServiceProviderBuilder();
    }

    public static class ServiceProviderBuilder {
        private Long id;
        private Boolean verified;
        private String availability;
        private Long userId;
        private User user;
        private List<ProviderLocation> locations;
        private List<ProviderSkill> skills;
        private Integer completedJobsCount;
        private Integer rejectedJobsCount;
        private Double hourlyRate;
        private String providerType;
        private Long serviceCategoryId;
        private ServiceCategory category;

        public ServiceProviderBuilder() {
        }

        public ServiceProviderBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceProviderBuilder verified(Boolean verified) {
            this.verified = verified;
            return this;
        }
        public ServiceProviderBuilder availability(String availability) {
            this.availability = availability;
            return this;
        }
        public ServiceProviderBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        public ServiceProviderBuilder user(User user) {
            this.user = user;
            return this;
        }
        public ServiceProviderBuilder locations(List<ProviderLocation> locations) {
            this.locations = locations;
            return this;
        }
        public ServiceProviderBuilder skills(List<ProviderSkill> skills) {
            this.skills = skills;
            return this;
        }
        public ServiceProviderBuilder completedJobsCount(Integer completedJobsCount) {
            this.completedJobsCount = completedJobsCount;
            return this;
        }
        public ServiceProviderBuilder rejectedJobsCount(Integer rejectedJobsCount) {
            this.rejectedJobsCount = rejectedJobsCount;
            return this;
        }
        public ServiceProviderBuilder hourlyRate(Double hourlyRate) {
            this.hourlyRate = hourlyRate;
            return this;
        }
        public ServiceProviderBuilder providerType(String providerType) {
            this.providerType = providerType;
            return this;
        }
        public ServiceProviderBuilder serviceCategoryId(Long serviceCategoryId) {
            this.serviceCategoryId = serviceCategoryId;
            return this;
        }
        public ServiceProviderBuilder category(ServiceCategory category) {
            this.category = category;
            return this;
        }

        public ServiceProvider build() {
            return new ServiceProvider(this.id, this.verified, this.availability, this.userId, this.user, this.locations, this.skills, this.completedJobsCount, this.rejectedJobsCount, this.hourlyRate, this.providerType, this.serviceCategoryId, this.category);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean verified = false;

    @NotBlank(message = "Availability is required")
    @Pattern(regexp = "^(AVAILABLE|BUSY|OFFLINE)$", message = "Availability must be AVAILABLE, BUSY or OFFLINE")

    @Column(nullable = false)
    private String availability = "AVAILABLE";

    @NotNull(message = "UserId is required")
    @Min(value = 1, message = "UserId must be a valid value")
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProviderLocation> locations;

    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProviderSkill> skills;

    @Column(name = "completed_jobs_count", nullable = false)
    private Integer completedJobsCount = 0;

    @Column(name = "rejected_jobs_count", nullable = false)
    private Integer rejectedJobsCount = 0;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "provider_type")
    private String providerType;

    @Column(name = "service_category_id")
    private Long serviceCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_category_id", insertable = false, updatable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private ServiceCategory category;
}