package com.lifesamadhan.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class CreateProviderSkillDTO {

    public CreateProviderSkillDTO() {
    }

    public CreateProviderSkillDTO(Long providerId, Long serviceId, String status, Double hourlyRate) {
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.status = status;
        this.hourlyRate = hourlyRate;
    }

    @NotNull(message = "ProviderId is required")
    @Min(value = 1, message = "ProviderId must be a valid value")
    private Long providerId;
    
    @NotNull(message = "ServiceId is required")
    @Min(value = 1, message = "ServiceId must be a valid value")
    private Long serviceId;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE|PENDING|APPROVED)$", message = "Status must be ACTIVE, INACTIVE, PENDING, or APPROVED")

    private String status = "PENDING";
    
    private Double hourlyRate;

    public static CreateProviderSkillDTOBuilder builder() {
        return new CreateProviderSkillDTOBuilder();
    }

    public static class CreateProviderSkillDTOBuilder {
        private Long providerId;
        private Long serviceId;
        private Double hourlyRate;

        public CreateProviderSkillDTOBuilder() {
        }

        public CreateProviderSkillDTOBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public CreateProviderSkillDTOBuilder serviceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }
        public CreateProviderSkillDTOBuilder hourlyRate(Double hourlyRate) {
            this.hourlyRate = hourlyRate;
            return this;
        }

        public CreateProviderSkillDTO build() {
            CreateProviderSkillDTO obj = new CreateProviderSkillDTO();
            obj.setProviderId(this.providerId);
            obj.setServiceId(this.serviceId);
            obj.setHourlyRate(this.hourlyRate);
            return obj;
        }
    }
}