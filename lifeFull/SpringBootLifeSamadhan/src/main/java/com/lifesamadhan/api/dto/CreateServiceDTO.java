package com.lifesamadhan.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class CreateServiceDTO {

    public CreateServiceDTO() {
    }

    public CreateServiceDTO(String name, Long categoryId, String status) {
        this.name = name;
        this.categoryId = categoryId;
        this.status = status;
    }

    @NotBlank(message = "Service name is required")
    @Size(min = 3, max = 150, message = "Service name must be between 3 and 150 characters")
    private String name;
    
    @NotNull(message = "CategoryId is required")
    @Min(value = 1, message = "CategoryId must be a valid value")
    private Long categoryId;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")

    private String status = "ACTIVE";

    public static CreateServiceDTOBuilder builder() {
        return new CreateServiceDTOBuilder();
    }

    public static class CreateServiceDTOBuilder {
        private String name;
        private Long categoryId;

        public CreateServiceDTOBuilder() {
        }

        public CreateServiceDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public CreateServiceDTOBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public CreateServiceDTO build() {
            CreateServiceDTO obj = new CreateServiceDTO();
            obj.setName(this.name);
            obj.setCategoryId(this.categoryId);
            return obj;
        }
    }
}