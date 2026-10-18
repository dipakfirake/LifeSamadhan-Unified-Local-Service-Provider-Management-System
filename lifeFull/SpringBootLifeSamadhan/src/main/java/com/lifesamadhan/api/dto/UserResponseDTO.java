package com.lifesamadhan.api.dto;

import lombok.Data;
@Data

public class UserResponseDTO {

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String email, String mobile, String role, String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.status = status;
    }

    public UserResponseDTO(Long id, String name, String email, String mobile, String role, String status, java.time.LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String role;
    private String status;
    private java.time.LocalDateTime createdAt;

    public static UserResponseDTOBuilder builder() {
        return new UserResponseDTOBuilder();
    }

    public static class UserResponseDTOBuilder {
        private Long id;
        private String name;
        private String email;
        private String mobile;
        private String role;
        private String status;
        private java.time.LocalDateTime createdAt;

        public UserResponseDTOBuilder() {
        }

        public UserResponseDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public UserResponseDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public UserResponseDTOBuilder email(String email) {
            this.email = email;
            return this;
        }
        public UserResponseDTOBuilder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }
        public UserResponseDTOBuilder role(String role) {
            this.role = role;
            return this;
        }
        public UserResponseDTOBuilder status(String status) {
            this.status = status;
            return this;
        }
        public UserResponseDTOBuilder createdAt(java.time.LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserResponseDTO build() {
            UserResponseDTO obj = new UserResponseDTO();
            obj.setId(this.id);
            obj.setName(this.name);
            obj.setEmail(this.email);
            obj.setMobile(this.mobile);
            obj.setRole(this.role);
            obj.setStatus(this.status);
            obj.setCreatedAt(this.createdAt);
            return obj;
        }
    }
}