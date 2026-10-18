package com.lifesamadhan.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class UpdateCategoryDTO {

    public UpdateCategoryDTO() {
    }

    public UpdateCategoryDTO(String name, String status) {
        this.name = name;
        this.status = status;
    }

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    private String name;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")
    private String status;

    public static UpdateCategoryDTOBuilder builder() {
        return new UpdateCategoryDTOBuilder();
    }

    public static class UpdateCategoryDTOBuilder {
        private String name;
        private String status;

        public UpdateCategoryDTOBuilder() {
        }

        public UpdateCategoryDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public UpdateCategoryDTOBuilder status(String status) {
            this.status = status;
            return this;
        }

        public UpdateCategoryDTO build() {
            UpdateCategoryDTO obj = new UpdateCategoryDTO();
            obj.setName(this.name);
            obj.setStatus(this.status);
            return obj;
        }
    }
}