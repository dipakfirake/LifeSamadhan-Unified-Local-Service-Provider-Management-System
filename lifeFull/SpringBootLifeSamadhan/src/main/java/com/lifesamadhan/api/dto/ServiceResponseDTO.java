package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class ServiceResponseDTO {

    public ServiceResponseDTO() {
    }

    public ServiceResponseDTO(Long id, String name, Long categoryId, String status, String categoryName) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.status = status;
        this.categoryName = categoryName;
    }

    private Long id;
    private String name;
    private Long categoryId;
    private String status;
    private String categoryName;

    public static ServiceResponseDTOBuilder builder() {
        return new ServiceResponseDTOBuilder();
    }

    public static class ServiceResponseDTOBuilder {
        private Long id;
        private String name;
        private Long categoryId;
        private String status;
        private String categoryName;

        public ServiceResponseDTOBuilder() {
        }

        public ServiceResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceResponseDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public ServiceResponseDTOBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }
        public ServiceResponseDTOBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ServiceResponseDTOBuilder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public ServiceResponseDTO build() {
            ServiceResponseDTO obj = new ServiceResponseDTO();
            obj.setId(this.id);
            obj.setName(this.name);
            obj.setCategoryId(this.categoryId);
            obj.setStatus(this.status);
            obj.setCategoryName(this.categoryName);
            return obj;
        }
    }
}