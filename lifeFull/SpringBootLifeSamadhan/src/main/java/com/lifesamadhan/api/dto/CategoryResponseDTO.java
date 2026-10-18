package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class CategoryResponseDTO {

    public CategoryResponseDTO() {
    }

    public CategoryResponseDTO(Long id, String name, String status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    private Long id;
    private String name;
    private String status;

    public static CategoryResponseDTOBuilder builder() {
        return new CategoryResponseDTOBuilder();
    }

    public static class CategoryResponseDTOBuilder {
        private Long id;
        private String name;
        private String status;

        public CategoryResponseDTOBuilder() {
        }

        public CategoryResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public CategoryResponseDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public CategoryResponseDTOBuilder status(String status) {
            this.status = status;
            return this;
        }

        public CategoryResponseDTO build() {
            CategoryResponseDTO obj = new CategoryResponseDTO();
            obj.setId(this.id);
            obj.setName(this.name);
            obj.setStatus(this.status);
            return obj;
        }
    }
}