package com.lifesamadhan.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class CreateCategoryDTO {

    public CreateCategoryDTO() {
    }

    public CreateCategoryDTO(String name, String status) {
        this.name = name;
        this.status = status;
    }

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    private String name;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")

    private String status = "ACTIVE";

    public static CreateCategoryDTOBuilder builder() {
        return new CreateCategoryDTOBuilder();
    }

    public static class CreateCategoryDTOBuilder {
        private String name;

        public CreateCategoryDTOBuilder() {
        }

        public CreateCategoryDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public CreateCategoryDTO build() {
            CreateCategoryDTO obj = new CreateCategoryDTO();
            obj.setName(this.name);
            return obj;
        }
    }
}