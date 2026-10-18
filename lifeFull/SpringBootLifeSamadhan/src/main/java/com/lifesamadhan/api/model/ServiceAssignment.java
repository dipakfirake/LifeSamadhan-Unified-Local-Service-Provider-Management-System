package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_assignments")
@Data

public class ServiceAssignment {

    public ServiceAssignment() {
    }

    public ServiceAssignment(Long id, Long requestId, Long providerId, String status, String otp, LocalDateTime assignedAt, LocalDateTime acceptedAt, LocalDateTime startedAt, LocalDateTime completedAt, ServiceRequest request, ServiceProvider provider, java.util.List<Payment> payments, java.util.List<Rating> ratings) {
        this.id = id;
        this.requestId = requestId;
        this.providerId = providerId;
        this.status = status != null ? status : "ASSIGNED";
        this.otp = otp;
        this.assignedAt = assignedAt;
        this.acceptedAt = acceptedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.request = request;
        this.provider = provider;
        this.payments = payments;
        this.ratings = ratings;
    }

    public static ServiceAssignmentBuilder builder() {
        return new ServiceAssignmentBuilder();
    }

    public static class ServiceAssignmentBuilder {
        private Long id;
        private Long requestId;
        private Long providerId;
        private String status;
        private String otp;
        private LocalDateTime assignedAt;
        private LocalDateTime acceptedAt;
        private LocalDateTime startedAt;
        private LocalDateTime completedAt;
        private ServiceRequest request;
        private ServiceProvider provider;
        private java.util.List<Payment> payments;
        private java.util.List<Rating> ratings;

        public ServiceAssignmentBuilder() {
        }

        public ServiceAssignmentBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceAssignmentBuilder requestId(Long requestId) {
            this.requestId = requestId;
            return this;
        }
        public ServiceAssignmentBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public ServiceAssignmentBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ServiceAssignmentBuilder otp(String otp) {
            this.otp = otp;
            return this;
        }
        public ServiceAssignmentBuilder assignedAt(LocalDateTime assignedAt) {
            this.assignedAt = assignedAt;
            return this;
        }
        public ServiceAssignmentBuilder acceptedAt(LocalDateTime acceptedAt) {
            this.acceptedAt = acceptedAt;
            return this;
        }
        public ServiceAssignmentBuilder startedAt(LocalDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }
        public ServiceAssignmentBuilder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }
        public ServiceAssignmentBuilder request(ServiceRequest request) {
            this.request = request;
            return this;
        }
        public ServiceAssignmentBuilder provider(ServiceProvider provider) {
            this.provider = provider;
            return this;
        }
        public ServiceAssignmentBuilder payments(java.util.List<Payment> payments) {
            this.payments = payments;
            return this;
        }
        public ServiceAssignmentBuilder ratings(java.util.List<Rating> ratings) {
            this.ratings = ratings;
            return this;
        }

        public ServiceAssignment build() {
            return new ServiceAssignment(this.id, this.requestId, this.providerId, this.status, this.otp, this.assignedAt, this.acceptedAt, this.startedAt, this.completedAt, this.request, this.provider, this.payments, this.ratings);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "RequestId is required")
    @Min(value = 1, message = "RequestId must be a valid value")
    @Column(name = "request_id", nullable = false)
    private Long requestId;

    @NotNull(message = "ProviderId is required")
    @Min(value = 1, message = "ProviderId must be a valid value")
    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @Pattern(regexp = "^(ASSIGNED|ACCEPTED|REJECTED|STARTED|IN_PROGRESS|COMPLETED|CANCELLED)$", message = "Status must be ASSIGNED, ACCEPTED, REJECTED, STARTED, IN_PROGRESS, COMPLETED or CANCELLED")

    @Column(nullable = false)
    private String status = "ASSIGNED";

    @Column(name = "otp")
    private String otp;

    @CreationTimestamp
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", insertable = false, updatable = false)
    private ServiceRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    private ServiceProvider provider;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<Payment> payments;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<Rating> ratings;
}