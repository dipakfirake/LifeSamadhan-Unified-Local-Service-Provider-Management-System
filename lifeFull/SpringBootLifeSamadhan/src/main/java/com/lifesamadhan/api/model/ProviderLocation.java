package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Entity
@Table(name = "provider_locations", uniqueConstraints = @UniqueConstraint(columnNames = { "provider_id",
        "location_id" }))
@Data

public class ProviderLocation {

    public ProviderLocation() {
    }

    public ProviderLocation(Long id, Long providerId, Long locationId, String status, ServiceProvider provider, Location location) {
        this.id = id;
        this.providerId = providerId;
        this.locationId = locationId;
        this.status = status != null ? status : "ACTIVE";
        this.provider = provider;
        this.location = location;
    }

    public static ProviderLocationBuilder builder() {
        return new ProviderLocationBuilder();
    }

    public static class ProviderLocationBuilder {
        private Long id;
        private Long providerId;
        private Long locationId;
        private String status;
        private ServiceProvider provider;
        private Location location;

        public ProviderLocationBuilder() {
        }

        public ProviderLocationBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProviderLocationBuilder providerId(Long providerId) {
            this.providerId = providerId;
            return this;
        }
        public ProviderLocationBuilder locationId(Long locationId) {
            this.locationId = locationId;
            return this;
        }
        public ProviderLocationBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ProviderLocationBuilder provider(ServiceProvider provider) {
            this.provider = provider;
            return this;
        }
        public ProviderLocationBuilder location(Location location) {
            this.location = location;
            return this;
        }

        public ProviderLocation build() {
            return new ProviderLocation(this.id, this.providerId, this.locationId, this.status, this.provider, this.location);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "ProviderId is required")
    @Min(value = 1, message = "ProviderId must be a valid value")
    @Column(name = "provider_id", nullable = false)
    private Long providerId;

    @NotNull(message = "LocationId is required")
    @Min(value = 1, message = "LocationId must be a valid value")
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    @Pattern(regexp = "^(ACTIVE|INACTIVE|PENDING|APPROVED)$", message = "Status must be ACTIVE, INACTIVE, PENDING, or APPROVED")

    @Column(nullable = false)
    private String status = "ACTIVE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private ServiceProvider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", insertable = false, updatable = false)
    private Location location;
}
