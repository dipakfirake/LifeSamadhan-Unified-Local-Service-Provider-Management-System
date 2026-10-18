package com.lifesamadhan.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

public class UserAssignmentDTO {

    public UserAssignmentDTO() {
    }

    public UserAssignmentDTO(Long id, String name, String email, String mobile, String role, String status, LocalDateTime createdAt) {
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
    private LocalDateTime createdAt;

    public static UserAssignmentDTOBuilder builder() {
        return new UserAssignmentDTOBuilder();
    }

    public static class UserAssignmentDTOBuilder {
        private Long id;
        private String name;
        private String email;
        private String mobile;
        private String role;
        private String status;
        private LocalDateTime createdAt;

        public UserAssignmentDTOBuilder() {
        }

        public UserAssignmentDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public UserAssignmentDTOBuilder name(String name) {
            this.name = name;
            return this;
        }
        public UserAssignmentDTOBuilder email(String email) {
            this.email = email;
            return this;
        }
        public UserAssignmentDTOBuilder mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }
        public UserAssignmentDTOBuilder role(String role) {
            this.role = role;
            return this;
        }
        public UserAssignmentDTOBuilder status(String status) {
            this.status = status;
            return this;
        }
        public UserAssignmentDTOBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserAssignmentDTO build() {
            UserAssignmentDTO obj = new UserAssignmentDTO();
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