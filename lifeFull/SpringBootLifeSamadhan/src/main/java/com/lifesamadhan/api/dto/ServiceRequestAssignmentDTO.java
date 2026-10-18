package com.lifesamadhan.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

public class ServiceRequestAssignmentDTO {

    public ServiceRequestAssignmentDTO() {
    }

    public ServiceRequestAssignmentDTO(Long id, Long customerId, Long serviceId, Long locationId, String status, LocalDateTime createdAt, CustomerAssignmentDTO customer) {
        this.id = id;
        this.customerId = customerId;
        this.serviceId = serviceId;
        this.locationId = locationId;
        this.status = status;
        this.createdAt = createdAt;
        this.customer = customer;
    }

    
    private Long id;
    private Long customerId;
    private Long serviceId;
    private Long locationId;
    private String status;
    private LocalDateTime createdAt;
    private CustomerAssignmentDTO customer;

    public static ServiceRequestAssignmentDTOBuilder builder() {
        return new ServiceRequestAssignmentDTOBuilder();
    }

    public static class ServiceRequestAssignmentDTOBuilder {
        private Long id;
        private Long customerId;
        private Long serviceId;
        private Long locationId;
        private String status;
        private LocalDateTime createdAt;
        private CustomerAssignmentDTO customer;

        public ServiceRequestAssignmentDTOBuilder() {
        }

        public ServiceRequestAssignmentDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceRequestAssignmentDTOBuilder customerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }
        public ServiceRequestAssignmentDTOBuilder serviceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }
        public ServiceRequestAssignmentDTOBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public ServiceRequestAssignmentDTOBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ServiceRequestAssignmentDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public ServiceRequestAssignmentDTOBuilder customer(CustomerAssignmentDTO customer) {
            this.customer = customer;
            return this;
        }

        public ServiceRequestAssignmentDTO build() {
            ServiceRequestAssignmentDTO obj = new ServiceRequestAssignmentDTO();
            obj.setId(this.id);
            obj.setCustomerId(this.customerId);
            obj.setServiceId(this.serviceId);
            obj.setLocationId(this.locationId);
            obj.setStatus(this.status);
            obj.setCreatedAt(this.createdAt);
            obj.setCustomer(this.customer);
            return obj;
        }
    }
}