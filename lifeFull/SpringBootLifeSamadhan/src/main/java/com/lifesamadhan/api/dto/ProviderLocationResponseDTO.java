package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class ProviderLocationResponseDTO {

    public ProviderLocationResponseDTO() {
    }

    public ProviderLocationResponseDTO(Long id, Long providerId, Long locationId, String status, String providerName, String locationDetails) {
        this.id = id;
        this.providerId = providerId;
        this.locationId = locationId;
        this.status = status;
        this.providerName = providerName;
        this.locationDetails = locationDetails;
    }

    private Long id;
    private Long providerId;
    private Long locationId;
    private String status;
    private String providerName;
    private String locationDetails;

    public static ProviderLocationResponseDTOBuilder builder() {
        return new ProviderLocationResponseDTOBuilder();
    }

    public static class ProviderLocationResponseDTOBuilder {
        private Long id;
        private Long providerId;
        private Long locationId;
        private String status;
        private String providerName;
        private String locationDetails;

        public ProviderLocationResponseDTOBuilder() {
        }

        public ProviderLocationResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProviderLocationResponseDTOBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public ProviderLocationResponseDTOBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public ProviderLocationResponseDTOBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ProviderLocationResponseDTOBuilder providerName(String providerName) {
            this.providerName = providerName;
            return this;
        }
        public ProviderLocationResponseDTOBuilder locationDetails(String locationDetails) {
            this.locationDetails = locationDetails;
            return this;
        }

        public ProviderLocationResponseDTO build() {
            ProviderLocationResponseDTO obj = new ProviderLocationResponseDTO();
            obj.setId(this.id);
            obj.setProviderId(this.providerId);
            obj.setLocationId(this.locationId);
            obj.setStatus(this.status);
            obj.setProviderName(this.providerName);
            obj.setLocationDetails(this.locationDetails);
            return obj;
        }
    }
}