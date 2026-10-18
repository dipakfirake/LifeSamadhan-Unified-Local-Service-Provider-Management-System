package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data

public class Payment {

    public Payment() {
    }

    public Payment(Long id, Long assignmentId, BigDecimal amount, String paymentStatus, String razorpayOrderId, LocalDateTime createdAt, ServiceAssignment assignment) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.amount = amount;
        this.paymentStatus = paymentStatus != null ? paymentStatus : "PENDING";
        this.razorpayOrderId = razorpayOrderId;
        this.createdAt = createdAt;
        this.assignment = assignment;
    }

    public static PaymentBuilder builder() {
        return new PaymentBuilder();
    }

    public static class PaymentBuilder {
        private Long id;
        private Long assignmentId;
        private BigDecimal amount;
        private String paymentStatus;
        private String razorpayOrderId;
        private LocalDateTime createdAt;
        private ServiceAssignment assignment;

        public PaymentBuilder() {
        }

        public PaymentBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public PaymentBuilder assignmentId(Long assignmentId) {
            this.assignmentId = assignmentId;
            return this;
        }
        public PaymentBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        public PaymentBuilder paymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }
        public PaymentBuilder razorpayOrderId(String razorpayOrderId) {
            this.razorpayOrderId = razorpayOrderId;
            return this;
        }
        public PaymentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public PaymentBuilder assignment(ServiceAssignment assignment) {
            this.assignment = assignment;
            return this;
        }

        public Payment build() {
            return new Payment(this.id, this.assignmentId, this.amount, this.paymentStatus, this.razorpayOrderId, this.createdAt, this.assignment);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "AssignmentId is required")
    @Min(value = 1, message = "AssignmentId must be a valid value")
    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Pattern(regexp = "^(PENDING|COMPLETED|FAILED|REFUNDED)$", message = "Payment status must be PENDING, COMPLETED, FAILED or REFUNDED")

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus = "PENDING";

    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", insertable = false, updatable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private ServiceAssignment assignment;
}