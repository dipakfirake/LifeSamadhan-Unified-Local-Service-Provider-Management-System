package com.lifesamadhan.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class CreateProviderLocationDTO {

    public CreateProviderLocationDTO() {
    }

    public CreateProviderLocationDTO(Long providerId, Long locationId, String status) {
        this.providerId = providerId;
        this.locationId = locationId;
        this.status = status;
    }

    @NotNull(message = "ProviderId is required")
    @Min(value = 1, message = "ProviderId must be a valid value")
    private Long providerId;
    
    @NotNull(message = "LocationId is required")
    @Min(value = 1, message = "LocationId must be a valid value")
    private Long locationId;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE|PENDING|APPROVED)$", message = "Status must be ACTIVE, INACTIVE, PENDING, or APPROVED")

    private String status = "PENDING";

    public static CreateProviderLocationDTOBuilder builder() {
        return new CreateProviderLocationDTOBuilder();
    }

    public static class CreateProviderLocationDTOBuilder {
        private Long providerId;
        private Long locationId;

        public CreateProviderLocationDTOBuilder() {
        }

        public CreateProviderLocationDTOBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public CreateProviderLocationDTOBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }

        public CreateProviderLocationDTO build() {
            CreateProviderLocationDTO obj = new CreateProviderLocationDTO();
            obj.setProviderId(this.providerId);
            obj.setLocationId(this.locationId);
            return obj;
        }
    }
}