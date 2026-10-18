package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "service_requests")
@Data

public class ServiceRequest {

    public ServiceRequest() {
    }

    public ServiceRequest(Long id, Long customerId, Long serviceId, Long locationId, Long providerId, String status, String serviceAddress, LocalDateTime scheduledDate, Double amount, String paymentStatus, Double paidAmount, LocalDateTime completionDate, LocalDateTime createdAt, CustomerProfile customer, Service service, Location location, java.util.List<ServiceAssignment> assignments) {
        this.id = id;
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.locationId = locationId;
        this.providerId = providerId;
        this.status = status != null ? status : "PENDING";
        this.serviceAddress = serviceAddress;
        this.scheduledDate = scheduledDate;
        this.amount = amount;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "PENDING";
        this.paidAmount = paidAmount != null ? paidAmount : 0.0;
        this.completionDate = completionDate;
        this.createdAt = createdAt;
        this.customer = customer;
        this.service = service;
        this.location = location;
        this.assignments = assignments;
    }

    public static ServiceRequestBuilder builder() {
        return new ServiceRequestBuilder();
    }

    public static class ServiceRequestBuilder {
        private Long id;
        private Long customerId;
        private Long serviceId;
        private Long locationId;
        private Long providerId;
        private String status;
        private String serviceAddress;
        private LocalDateTime scheduledDate;
        private Double amount;
        private String paymentStatus;
        private Double paidAmount;
        private LocalDateTime completionDate;
        private LocalDateTime createdAt;
        private CustomerProfile customer;
        private Service service;
        private Location location;
        private java.util.List<ServiceAssignment> assignments;

        public ServiceRequestBuilder() {
        }

        public ServiceRequestBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceRequestBuilder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }
        public ServiceRequestBuilder serviceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }
        public ServiceRequestBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public ServiceRequestBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public ServiceRequestBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ServiceRequestBuilder serviceAddress(String serviceAddress) {
            this.serviceAddress = serviceAddress;
            return this;
        }
        public ServiceRequestBuilder scheduledDate(LocalDateTime scheduledDate) {
            this.scheduledDate = scheduledDate;
            return this;
        }
        public ServiceRequestBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }
        public ServiceRequestBuilder paymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }
        public ServiceRequestBuilder paidAmount(Double paidAmount) {
            this.paidAmount = paidAmount;
            return this;
        }
        public ServiceRequestBuilder completionDate(LocalDateTime completionDate) {
            this.completionDate = completionDate;
            return this;
        }
        public ServiceRequestBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public ServiceRequestBuilder customer(CustomerProfile customer) {
            this.customer = customer;
            return this;
        }
        public ServiceRequestBuilder service(Service service) {
            this.service = service;
            return this;
        }
        public ServiceRequestBuilder location(Location location) {
            this.location = location;
            return this;
        }
        public ServiceRequestBuilder assignments(java.util.List<ServiceAssignment> assignments) {
            this.assignments = assignments;
            return this;
        }

        public ServiceRequest build() {
            return new ServiceRequest(this.id, this.customerId, this.serviceId, this.locationId, this.providerId, this.status, this.serviceAddress, this.scheduledDate, this.amount, this.paymentStatus, this.paidAmount, this.completionDate, this.createdAt, this.customer, this.service, this.location, this.assignments);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "CustomerId is required")
    @Min(value = 1, message = "CustomerId must be a valid value")
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull(message = "ServiceId is required")
    @Min(value = 1, message = "ServiceId must be a valid value")
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "provider_id")
    private Long providerId;

    @Pattern(regexp = "^(PENDING|ASSIGNED|IN_PROGRESS|STARTED|COMPLETED|CANCELLED)$", message = "Status must be PENDING, ASSIGNED, IN_PROGRESS, STARTED, COMPLETED or CANCELLED")

    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "service_address")
    private String serviceAddress;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_status")

    private String paymentStatus = "PENDING";

    @Column(name = "paid_amount")

    private Double paidAmount = 0.0;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private CustomerProfile customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", insertable = false, updatable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private Location location;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private java.util.List<ServiceAssignment> assignments;
}