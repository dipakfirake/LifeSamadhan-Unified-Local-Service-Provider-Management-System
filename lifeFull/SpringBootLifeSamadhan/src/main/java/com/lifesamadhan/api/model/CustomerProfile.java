package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "customer_profiles")
@Data
public class CustomerProfile {
    
    public CustomerProfile() {
    }

    public CustomerProfile(Long id, Long userId, Long locationId, String address, User user, Location location) {
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
        this.address = address;
        this.user = user;
        this.location = location;
    }

    public static CustomerProfileBuilder builder() {
        return new CustomerProfileBuilder();
    }

    public static class CustomerProfileBuilder {
        private Long id;
        private Long userId;
        private Long locationId;
        private String address;
        private User user;
        private Location location;

        public CustomerProfileBuilder() {
        }

        public CustomerProfileBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CustomerProfileBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public CustomerProfileBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }

        public CustomerProfileBuilder address(String address) {
            this.address = address;
            return this;
        }

        public CustomerProfileBuilder user(User user) {
            this.user = user;
            return this;
        }

        public CustomerProfileBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public CustomerProfile build() {
            return new CustomerProfile(id, userId, locationId, address, user, location);
        }

        public String toString() {
            return "CustomerProfile.CustomerProfileBuilder(id=" + this.id + ", userId=" + this.userId + ", locationId=" + this.locationId + ", address=" + this.address + ", user=" + this.user + ", location=" + this.location + ")";
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "UserId is required")
    @Min(value = 1, message = "UserId must be a valid value")
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    
    @Column(name = "location_id")
    private Long locationId;
    
    @Column(length = 255)
    private String address;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private Location location;
}