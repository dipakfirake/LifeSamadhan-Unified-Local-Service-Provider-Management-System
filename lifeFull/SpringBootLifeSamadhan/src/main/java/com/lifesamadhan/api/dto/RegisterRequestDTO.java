package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data

public class RegisterRequestDTO {

    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String name, String email, String mobile, String password, String role, String address, String providerType, Double hourlyRate, Long locationId, Long serviceCategoryId, String skills, String city, String state) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.role = role;
        this.address = address;
        this.providerType = providerType;
        this.hourlyRate = hourlyRate;
        this.locationId = locationId;
        this.serviceCategoryId = serviceCategoryId;
        this.skills = skills;
        this.city = city;
        this.state = state;
    }

    
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String role; 

    
    private String address;

    
    private String providerType;
    private Double hourlyRate;
    private Long locationId;
    private Long serviceCategoryId;
    private String skills; 

    
    private String city;
    private String state;

    public static RegisterRequestDTOBuilder builder() {
        return new RegisterRequestDTOBuilder();
    }

    public static class RegisterRequestDTOBuilder {
        private String name;
        private String email;
        private String mobile;
        private String password;
        private String role;
        private String address;
        private String providerType;
        private Double hourlyRate;
        private Long locationId;
        private Long serviceCategoryId;
        private String skills;
        private String city;
        private String state;

        public RegisterRequestDTOBuilder() {
        }

        public RegisterRequestDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public RegisterRequestDTOBuilder email(String email) {
            this.email = email;
            return this;
        }
        public RegisterRequestDTOBuilder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }
        public RegisterRequestDTOBuilder password(String password) {
            this.password = password;
            return this;
        }
        public RegisterRequestDTOBuilder role(String role) {
            this.role = role;
            return this;
        }
        public RegisterRequestDTOBuilder address(String address) {
            this.address = address;
            return this;
        }
        public RegisterRequestDTOBuilder providerType(String providerType) {
            this.providerType = providerType;
            return this;
        }
        public RegisterRequestDTOBuilder hourlyRate(Double hourlyRate) {
            this.hourlyRate = hourlyRate;
            return this;
        }
        public RegisterRequestDTOBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public RegisterRequestDTOBuilder serviceCategoryId(Long serviceCategoryId) {
            this.serviceCategoryId = serviceCategoryId;
            return this;
        }
        public RegisterRequestDTOBuilder skills(String skills) {
            this.skills = skills;
            return this;
        }
        public RegisterRequestDTOBuilder city(String city) {
            this.city = city;
            return this;
        }
        public RegisterRequestDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public RegisterRequestDTO build() {
            RegisterRequestDTO obj = new RegisterRequestDTO();
            obj.setName(this.name);
            obj.setEmail(this.email);
            obj.setMobile(this.mobile);
            obj.setPassword(this.password);
            obj.setRole(this.role);
            obj.setAddress(this.address);
            obj.setProviderType(this.providerType);
            obj.setHourlyRate(this.hourlyRate);
            obj.setLocationId(this.locationId);
            obj.setServiceCategoryId(this.serviceCategoryId);
            obj.setSkills(this.skills);
            obj.setCity(this.city);
            obj.setState(this.state);
            return obj;
        }
    }
}