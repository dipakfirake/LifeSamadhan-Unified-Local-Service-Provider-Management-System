package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Entity
@Table(name = "locations", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"state", "district", "pincode"}))
@Data

public class Location {
    
    public Location() {
    }

    public Location(Long id, String country, String state, String district, String pincode, String status) {
        this.id = id;
        this.country = country;
        this.state = state;
        this.district = district;
        this.pincode = pincode;
        this.status = status != null ? status : "ACTIVE";
    }

    public static LocationBuilder builder() {
        return new LocationBuilder();
    }

    public static class LocationBuilder {
        private Long id;
        private String country;
        private String state;
        private String district;
        private String pincode;
        private String status;

        public LocationBuilder() {
        }

        public LocationBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public LocationBuilder country(String country) {
            this.country = country;
            return this;
        }
        public LocationBuilder state(String state) {
            this.state = state;
            return this;
        }
        public LocationBuilder district(String district) {
            this.district = district;
            return this;
        }
        public LocationBuilder pincode(String pincode) {
            this.pincode = pincode;
            return this;
        }
        public LocationBuilder status(String status) {
            this.status = status;
            return this;
        }

        public Location build() {
            return new Location(this.id, this.country, this.state, this.district, this.pincode, this.status);
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String country;
    
    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String state;
    
    @NotBlank(message = "District is required")
    @Size(max = 100, message = "District name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String district;
    
    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode format")
    @Column(nullable = false, length = 6)
    private String pincode;
    
    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")

    @Column(nullable = false)
    private String status = "ACTIVE";
}