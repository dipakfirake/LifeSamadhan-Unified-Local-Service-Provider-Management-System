package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Entity
@Table(name = "provider_skills", uniqueConstraints = @UniqueConstraint(columnNames = { "provider_id", "service_id" }))
@Data

public class ProviderSkill {

    public ProviderSkill() {
    }

    public ProviderSkill(Long id, Long providerId, Long serviceId, String status, ServiceProvider provider, Service service) {
        this.id = id;
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.status = status != null ? status : "ACTIVE";
        this.provider = provider;
        this.service = service;
    }

    public static ProviderSkillBuilder builder() {
        return new ProviderSkillBuilder();
    }

    public static class ProviderSkillBuilder {
        private Long id;
        private Long providerId;
        private Long serviceId;
        private String status;
        private ServiceProvider provider;
        private Service service;

        public ProviderSkillBuilder() {
        }

        public ProviderSkillBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProviderSkillBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public ProviderSkillBuilder serviceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }
        public ProviderSkillBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ProviderSkillBuilder provider(ServiceProvider provider) {
            this.provider = provider;
            return this;
        }
        public ProviderSkillBuilder service(Service service) {
            this.service = service;
            return this;
        }

        public ProviderSkill build() {
            return new ProviderSkill(this.id, this.providerId, this.serviceId, this.status, this.provider, this.service);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ProviderId is required")
    @Min(value = 1, message = "ProviderId must be a valid value")
    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @NotNull(message = "ServiceId is required")
    @Min(value = 1, message = "ServiceId must be a valid value")
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(regexp = "^(ACTIVE|INACTIVE|PENDING|APPROVED)$", message = "Status must be ACTIVE, INACTIVE, PENDING, or APPROVED")

    @Column(nullable = false)
    private String status = "ACTIVE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private ServiceProvider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private Service service;

    @PrePersist
    @PreUpdate
    private void validateEntity() {
        if (providerId == null || providerId <= 0) {
            throw new IllegalArgumentException("Provider ID must be a positive number");
        }
        if (serviceId == null || serviceId <= 0) {
            throw new IllegalArgumentException("Service ID must be a positive number");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty");
        }
    }
}
