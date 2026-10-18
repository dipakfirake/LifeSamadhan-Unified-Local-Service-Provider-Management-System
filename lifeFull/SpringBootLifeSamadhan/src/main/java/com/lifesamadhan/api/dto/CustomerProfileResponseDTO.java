package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data

public class CustomerProfileResponseDTO {

    public CustomerProfileResponseDTO() {
    }

    public CustomerProfileResponseDTO(UserResponseDTO user, Long locationId, String address, String district, String state, String pincode) {
        this.user = user;
        this.locationId = locationId;
        this.address = address;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
    }

    private UserResponseDTO user;
    private Long locationId;
    private String address;
    private String district;
    private String state;
    private String pincode;

    public static CustomerProfileResponseDTOBuilder builder() {
        return new CustomerProfileResponseDTOBuilder();
    }

    public static class CustomerProfileResponseDTOBuilder {
        private UserResponseDTO user;
        private Long locationId;
        private String address;
        private String district;
        private String state;
        private String pincode;

        public CustomerProfileResponseDTOBuilder() {
        }

        public CustomerProfileResponseDTOBuilder user(UserResponseDTO user) {
            this.user = user;
            return this;
        }
        public CustomerProfileResponseDTOBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public CustomerProfileResponseDTOBuilder address(String address) {
            this.address = address;
            return this;
        }
        public CustomerProfileResponseDTOBuilder district(String district) {
            this.district = district;
            return this;
        }
        public CustomerProfileResponseDTOBuilder state(String state) {
            this.state = state;
            return this;
        }
        public CustomerProfileResponseDTOBuilder pincode(String pincode) {
            this.pincode = pincode;
            return this;
        }

        public CustomerProfileResponseDTO build() {
            CustomerProfileResponseDTO obj = new CustomerProfileResponseDTO();
            obj.setUser(this.user);
            obj.setLocationId(this.locationId);
            obj.setAddress(this.address);
            obj.setDistrict(this.district);
            obj.setState(this.state);
            obj.setPincode(this.pincode);
            return obj;
        }
    }
}