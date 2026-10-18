package com.lifesamadhan.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class UpdateLocationDTO {

    public UpdateLocationDTO() {
    }

    public UpdateLocationDTO(String country, String state, String district, String pincode, String status) {
        this.country = country;
        this.state = state;
        this.district = district;
        this.pincode = pincode;
        this.status = status;
    }

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    private String country;
    
    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State name cannot exceed 100 characters")
    private String state;
    
    @NotBlank(message = "District is required")
    @Size(max = 100, message = "District name cannot exceed 100 characters")
    private String district;
    
    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode format")
    private String pincode;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")
    private String status;

    public static UpdateLocationDTOBuilder builder() {
        return new UpdateLocationDTOBuilder();
    }

    public static class UpdateLocationDTOBuilder {
        private String country;
        private String state;
        private String district;
        private String pincode;
        private String status;

        public UpdateLocationDTOBuilder() {
        }

        public UpdateLocationDTOBuilder country(String country) {
            this.country = country;
            return this;
        }
        public UpdateLocationDTOBuilder state(String state) {
            this.state = state;
            return this;
        }
        public UpdateLocationDTOBuilder district(String district) {
            this.district = district;
            return this;
        }
        public UpdateLocationDTOBuilder pincode(String pincode) {
            this.pincode = pincode;
            return this;
        }
        public UpdateLocationDTOBuilder status(String status) {
            this.status = status;
            return this;
        }

        public UpdateLocationDTO build() {
            UpdateLocationDTO obj = new UpdateLocationDTO();
            obj.setCountry(this.country);
            obj.setState(this.state);
            obj.setDistrict(this.district);
            obj.setPincode(this.pincode);
            obj.setStatus(this.status);
            return obj;
        }
    }
}