package com.lifesamadhan.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CustomerAssignmentDTO {

    public CustomerAssignmentDTO() {
    }

    public CustomerAssignmentDTO(Long id, Long userId, Long locationId, String address, UserAssignmentDTO user) {
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
        this.address = address;
        this.user = user;
    }

    
    private Long id;
    private Long userId;
    private Long locationId;
    private String address;
    private UserAssignmentDTO user;

    public static CustomerAssignmentDTOBuilder builder() {
        return new CustomerAssignmentDTOBuilder();
    }

    public static class CustomerAssignmentDTOBuilder {
        private Long id;
        private Long userId;
        private Long locationId;
        private String address;
        private UserAssignmentDTO user;

        public CustomerAssignmentDTOBuilder() {
        }

        public CustomerAssignmentDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public CustomerAssignmentDTOBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        public CustomerAssignmentDTOBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public CustomerAssignmentDTOBuilder address(String address) {
            this.address = address;
            return this;
        }
        public CustomerAssignmentDTOBuilder user(UserAssignmentDTO user) {
            this.user = user;
            return this;
        }

        public CustomerAssignmentDTO build() {
            CustomerAssignmentDTO obj = new CustomerAssignmentDTO();
            obj.setId(this.id);
            obj.setUserId(this.userId);
            obj.setLocationId(this.locationId);
            obj.setAddress(this.address);
            obj.setUser(this.user);
            return obj;
        }
    }
}