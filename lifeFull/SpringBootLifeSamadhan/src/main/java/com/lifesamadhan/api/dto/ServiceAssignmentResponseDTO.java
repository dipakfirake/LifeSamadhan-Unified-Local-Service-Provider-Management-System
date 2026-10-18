package com.lifesamadhan.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

public class ServiceAssignmentResponseDTO {

    public ServiceAssignmentResponseDTO() {
    }

    public ServiceAssignmentResponseDTO(Long id, Long requestId, Long providerId, String status, String otp, String serviceName, String providerName, Double hourlyRate, LocalDateTime assignedAt, LocalDateTime acceptedAt, ServiceRequestAssignmentDTO request, String paymentStatus, Double paidAmount, Integer ratingStars, String ratingFeedback, LocalDateTime completedAt) {
        this.id = id;
        this.requestId = requestId;
        this.providerId = providerId;
        this.status = status;
        this.otp = otp;
        this.serviceName = serviceName;
        this.providerName = providerName;
        this.hourlyRate = hourlyRate;
        this.assignedAt = assignedAt;
        this.acceptedAt = acceptedAt;
        this.request = request;
        this.paymentStatus = paymentStatus;
        this.paidAmount = paidAmount;
        this.ratingStars = ratingStars;
        this.ratingFeedback = ratingFeedback;
        this.completedAt = completedAt;
    }


    private Long id;
    private Long requestId;
    private Long providerId;
    private String status;
    private String otp;
    private String serviceName;
    private String providerName;
    private Double hourlyRate;
    private LocalDateTime assignedAt;
    private LocalDateTime acceptedAt;
    private ServiceRequestAssignmentDTO request;
    private String paymentStatus;
    private Double paidAmount;
    private Integer ratingStars; 
    private String ratingFeedback;
    private LocalDateTime completedAt;

    public static ServiceAssignmentResponseDTOBuilder builder() {
        return new ServiceAssignmentResponseDTOBuilder();
    }

    public static class ServiceAssignmentResponseDTOBuilder {
        private Long id;
        private Long requestId;
        private Long providerId;
        private String status;
        private String otp;
        private String serviceName;
        private String providerName;
        private Double hourlyRate;
        private LocalDateTime assignedAt;
        private LocalDateTime acceptedAt;
        private ServiceRequestAssignmentDTO request;
        private String paymentStatus;
        private Double paidAmount;
        private Integer ratingStars;
        private String ratingFeedback;
        private LocalDateTime completedAt;

        public ServiceAssignmentResponseDTOBuilder() {
        }

        public ServiceAssignmentResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder requestId(Long requestId) {
            this.requestId = requestId;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder otp(String otp) {
            this.otp = otp;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder providerName(String providerName) {
            this.providerName = providerName;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder hourlyRate(Double hourlyRate) {
            this.hourlyRate = hourlyRate;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder assignedAt(LocalDateTime assignedAt) {
            this.assignedAt = assignedAt;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder acceptedAt(LocalDateTime acceptedAt) {
            this.acceptedAt = acceptedAt;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder request(ServiceRequestAssignmentDTO request) {
            this.request = request;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder paymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder paidAmount(Double paidAmount) {
            this.paidAmount = paidAmount;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder ratingStars(Integer ratingStars) {
            this.ratingStars = ratingStars;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder ratingFeedback(String ratingFeedback) {
            this.ratingFeedback = ratingFeedback;
            return this;
        }
        public ServiceAssignmentResponseDTOBuilder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public ServiceAssignmentResponseDTO build() {
            ServiceAssignmentResponseDTO obj = new ServiceAssignmentResponseDTO();
            obj.setId(this.id);
            obj.setRequestId(this.requestId);
            obj.setProviderId(this.providerId);
            obj.setStatus(this.status);
            obj.setOtp(this.otp);
            obj.setServiceName(this.serviceName);
            obj.setProviderName(this.providerName);
            obj.setHourlyRate(this.hourlyRate);
            obj.setAssignedAt(this.assignedAt);
            obj.setAcceptedAt(this.acceptedAt);
            obj.setRequest(this.request);
            obj.setPaymentStatus(this.paymentStatus);
            obj.setPaidAmount(this.paidAmount);
            obj.setRatingStars(this.ratingStars);
            obj.setRatingFeedback(this.ratingFeedback);
            obj.setCompletedAt(this.completedAt);
            return obj;
        }
    }
}