package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class ProviderResponseDTO {

    public ProviderResponseDTO() {
    }

    public ProviderResponseDTO(Long id, UserResponseDTO user, Boolean verified, String availability, Integer completedJobsCount, Integer rejectedJobsCount, Double hourlyRate, String providerType, Long serviceCategoryId, String categoryName, String city, String state) {
        this.id = id;
        this.user = user;
        this.verified = verified;
        this.availability = availability;
        this.completedJobsCount = completedJobsCount;
        this.rejectedJobsCount = rejectedJobsCount;
        this.hourlyRate = hourlyRate;
        this.providerType = providerType;
        this.serviceCategoryId = serviceCategoryId;
        this.categoryName = categoryName;
        this.city = city;
        this.state = state;
    }

    private Long id;
    private UserResponseDTO user;
    private Boolean verified;
    private String availability;
    private Integer completedJobsCount;
    private Integer rejectedJobsCount;
    private Double hourlyRate;
    private String providerType;
    private Long serviceCategoryId;
    private String categoryName;
    private String city;
    private String state;

    public static ProviderResponseDTOBuilder builder() {
        return new ProviderResponseDTOBuilder();
    }

    public static class ProviderResponseDTOBuilder {
        private Long id;
        private UserResponseDTO user;
        private Boolean verified;
        private String availability;
        private Integer completedJobsCount;
        private Integer rejectedJobsCount;
        private Double hourlyRate;
        private String providerType;
        private Long serviceCategoryId;
        private String categoryName;
        private String city;
        private String state;

        public ProviderResponseDTOBuilder() {
        }

        public ProviderResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProviderResponseDTOBuilder user(UserResponseDTO user) {
            this.user = user;
            return this;
        }
        public ProviderResponseDTOBuilder verified(Boolean verified) {
            this.verified = verified;
            return this;
        }
        public ProviderResponseDTOBuilder availability(String availability) {
            this.availability = availability;
            return this;
        }
        public ProviderResponseDTOBuilder completedJobsCount(Integer completedJobsCount) {
            this.completedJobsCount = completedJobsCount;
            return this;
        }
        public ProviderResponseDTOBuilder rejectedJobsCount(Integer rejectedJobsCount) {
            this.rejectedJobsCount = rejectedJobsCount;
            return this;
        }
        public ProviderResponseDTOBuilder hourlyRate(Double hourlyRate) {
            this.hourlyRate = hourlyRate;
            return this;
        }
        public ProviderResponseDTOBuilder providerType(String providerType) {
            this.providerType = providerType;
            return this;
        }
        public ProviderResponseDTOBuilder serviceCategoryId(Long serviceCategoryId) {
            this.serviceCategoryId = serviceCategoryId;
            return this;
        }
        public ProviderResponseDTOBuilder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }
        public ProviderResponseDTOBuilder city(String city) {
            this.city = city;
            return this;
        }
        public ProviderResponseDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public ProviderResponseDTO build() {
            ProviderResponseDTO obj = new ProviderResponseDTO();
            obj.setId(this.id);
            obj.setUser(this.user);
            obj.setVerified(this.verified);
            obj.setAvailability(this.availability);
            obj.setCompletedJobsCount(this.completedJobsCount);
            obj.setRejectedJobsCount(this.rejectedJobsCount);
            obj.setHourlyRate(this.hourlyRate);
            obj.setProviderType(this.providerType);
            obj.setServiceCategoryId(this.serviceCategoryId);
            obj.setCategoryName(this.categoryName);
            obj.setCity(this.city);
            obj.setState(this.state);
            return obj;
        }
    }
}