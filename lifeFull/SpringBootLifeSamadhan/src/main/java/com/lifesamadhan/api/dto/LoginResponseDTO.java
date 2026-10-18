package com.lifesamadhan.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Data

public class LoginResponseDTO {

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, String role, String message, String name, String email, String location, Long userId, Long providerId, Long customerProfileId) {
        this.token = token;
        this.role = role;
        this.message = message;
        this.name = name;
        this.email = email;
        this.location = location;
        this.userId = userId;
        this.providerId = providerId;
        this.customerProfileId = customerProfileId;
    }

    private String token;
    private String role;
    private String message;
    private String name;
    private String email;
    private String location;
    private Long userId;
    private Long providerId;
    private Long customerProfileId;

    public static LoginResponseDTOBuilder builder() {
        return new LoginResponseDTOBuilder();
    }

    public static class LoginResponseDTOBuilder {
        private String token;
        private String role;
        private String message;
        private String name;
        private String email;
        private String location;
        private Long userId;
        private Long providerId;
        private Long customerProfileId;

        public LoginResponseDTOBuilder() {
        }

        public LoginResponseDTOBuilder token(String token) {
            this.token = token;
            return this;
        }
        public LoginResponseDTOBuilder role(String role) {
            this.role = role;
            return this;
        }
        public LoginResponseDTOBuilder message(String message) {
            this.message = message;
            return this;
        }
        public LoginResponseDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public LoginResponseDTOBuilder email(String email) {
            this.email = email;
            return this;
        }
        public LoginResponseDTOBuilder location(String location) {
            this.location = location;
            return this;
        }
        public LoginResponseDTOBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        public LoginResponseDTOBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public LoginResponseDTOBuilder customerProfileId(Long customerProfileId) {
            this.customerProfileId = customerProfileId;
            return this;
        }

        public LoginResponseDTO build() {
            LoginResponseDTO obj = new LoginResponseDTO();
            obj.setToken(this.token);
            obj.setRole(this.role);
            obj.setMessage(this.message);
            obj.setName(this.name);
            obj.setEmail(this.email);
            obj.setLocation(this.location);
            obj.setUserId(this.userId);
            obj.setProviderId(this.providerId);
            obj.setCustomerProfileId(this.customerProfileId);
            return obj;
        }
    }
}