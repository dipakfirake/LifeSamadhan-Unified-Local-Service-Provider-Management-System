package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class SkillResponseDTO {

    public SkillResponseDTO() {
    }

    public SkillResponseDTO(Long id, Long providerId, Long serviceId, String status, String serviceName, String providerName) {
        this.id = id;
        this.providerId = providerId;
        this.serviceId = serviceId;
        this.status = status;
        this.serviceName = serviceName;
        this.providerName = providerName;
    }

    private Long id;
    private Long providerId;
    private Long serviceId;
    private String status;
    private String serviceName;
    private String providerName;

    public static SkillResponseDTOBuilder builder() {
        return new SkillResponseDTOBuilder();
    }

    public static class SkillResponseDTOBuilder {
        private Long id;
        private Long providerId;
        private Long serviceId;
        private String status;
        private String serviceName;
        private String providerName;

        public SkillResponseDTOBuilder() {
        }

        public SkillResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public SkillResponseDTOBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public SkillResponseDTOBuilder serviceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }
        public SkillResponseDTOBuilder status(String status) {
            this.status = status;
            return this;
        }
        public SkillResponseDTOBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }
        public SkillResponseDTOBuilder providerName(String providerName) {
            this.providerName = providerName;
            return this;
        }

        public SkillResponseDTO build() {
            SkillResponseDTO obj = new SkillResponseDTO();
            obj.setId(this.id);
            obj.setProviderId(this.providerId);
            obj.setServiceId(this.serviceId);
            obj.setStatus(this.status);
            obj.setServiceName(this.serviceName);
            obj.setProviderName(this.providerName);
            return obj;
        }
    }
}