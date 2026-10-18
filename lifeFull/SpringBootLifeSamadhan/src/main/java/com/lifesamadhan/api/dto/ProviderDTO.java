package com.lifesamadhan.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class ProviderDTO {

    public ProviderDTO() {
    }

    public ProviderDTO(Long id, Long providerId, String name, Double hourlyRate, Double rating, Integer completedJobs, Integer reviewCount, String providerType, String city) {
        this.id = id;
        this.providerId = providerId;
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.rating = rating;
        this.completedJobs = completedJobs;
        this.reviewCount = reviewCount;
        this.providerType = providerType;
        this.city = city;
    }

    private Long id;
    private Long providerId;
    private String name;
    private Double hourlyRate;
    private Double rating;
    private Integer completedJobs;
    private Integer reviewCount;
    private String providerType;
    private String city;

    public static ProviderDTOBuilder builder() {
        return new ProviderDTOBuilder();
    }

    public static class ProviderDTOBuilder {
        private Long id;
        private Long providerId;
        private String name;
        private Double hourlyRate;
        private Double rating;
        private Integer completedJobs;
        private Integer reviewCount;
        private String providerType;
        private String city;

        public ProviderDTOBuilder() {
        }

        public ProviderDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProviderDTOBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public ProviderDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public ProviderDTOBuilder hourlyRate(Double hourlyRate) {
            this.hourlyRate = hourlyRate;
            return this;
        }
        public ProviderDTOBuilder rating(Double rating) {
            this.rating = rating;
            return this;
        }
        public ProviderDTOBuilder completedJobs(Integer completedJobs) {
            this.completedJobs = completedJobs;
            return this;
        }
        public ProviderDTOBuilder reviewCount(Integer reviewCount) {
            this.reviewCount = reviewCount;
            return this;
        }
        public ProviderDTOBuilder providerType(String providerType) {
            this.providerType = providerType;
            return this;
        }
        public ProviderDTOBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ProviderDTO build() {
            ProviderDTO obj = new ProviderDTO();
            obj.setId(this.id);
            obj.setProviderId(this.providerId);
            obj.setName(this.name);
            obj.setHourlyRate(this.hourlyRate);
            obj.setRating(this.rating);
            obj.setCompletedJobs(this.completedJobs);
            obj.setReviewCount(this.reviewCount);
            obj.setProviderType(this.providerType);
            obj.setCity(this.city);
            return obj;
        }
    }
}