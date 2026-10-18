package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class LocationResponseDTO {

    public LocationResponseDTO() {
    }

    public LocationResponseDTO(Long id, String country, String state, String district, String pincode, String status) {
        this.id = id;
        this.country = country;
        this.state = state;
        this.district = district;
        this.pincode = pincode;
        this.status = status;
    }

    private Long id;
    private String country;
    private String state;
    private String district;
    private String pincode;
    private String status;
    

    public static LocationResponseDTOBuilder builder() {
        return new LocationResponseDTOBuilder();
    }

    public static class LocationResponseDTOBuilder {
        private Long id;
        private String country;
        private String state;
        private String district;
        private String pincode;
        private String status;

        public LocationResponseDTOBuilder() {
        }

        public LocationResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public LocationResponseDTOBuilder country(String country) {
            this.country = country;
            return this;
        }
        public LocationResponseDTOBuilder state(String state) {
            this.state = state;
            return this;
        }
        public LocationResponseDTOBuilder district(String district) {
            this.district = district;
            return this;
        }
        public LocationResponseDTOBuilder pincode(String pincode) {
            this.pincode = pincode;
            return this;
        }
        public LocationResponseDTOBuilder status(String status) {
            this.status = status;
            return this;
        }

        public LocationResponseDTO build() {
            LocationResponseDTO obj = new LocationResponseDTO();
            obj.setId(this.id);
            obj.setCountry(this.country);
            obj.setState(this.state);
            obj.setDistrict(this.district);
            obj.setPincode(this.pincode);
            obj.setStatus(this.status);
            return obj;
        }
    }
}