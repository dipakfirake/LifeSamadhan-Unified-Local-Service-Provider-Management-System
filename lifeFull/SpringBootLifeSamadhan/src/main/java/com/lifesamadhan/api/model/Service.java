package com.lifesamadhan.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Entity
@Table(name = "services", uniqueConstraints = @UniqueConstraint(columnNames = { "category_id", "name" }))
@Data

public class Service {

    public Service() {
    }

    public Service(Long id, Long categoryId, String name, String status, ServiceCategory category, java.util.List<ProviderSkill> providerSkills, java.util.List<ServiceRequest> serviceRequests) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.status = status != null ? status : "ACTIVE";
        this.category = category;
        this.providerSkills = providerSkills;
        this.serviceRequests = serviceRequests;
    }

    public static ServiceBuilder builder() {
        return new ServiceBuilder();
    }

    public static class ServiceBuilder {
        private Long id;
        private Long categoryId;
        private String name;
        private String status;
        private ServiceCategory category;
        private java.util.List<ProviderSkill> providerSkills;
        private java.util.List<ServiceRequest> serviceRequests;

        public ServiceBuilder() {
        }

        public ServiceBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceBuilder categoryId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }
        public ServiceBuilder name(String name) {
            this.name = name;
            return this;
        }
        public ServiceBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ServiceBuilder category(ServiceCategory category) {
            this.category = category;
            return this;
        }
        public ServiceBuilder providerSkills(java.util.List<ProviderSkill> providerSkills) {
            this.providerSkills = providerSkills;
            return this;
        }
        public ServiceBuilder serviceRequests(java.util.List<ServiceRequest> serviceRequests) {
            this.serviceRequests = serviceRequests;
            return this;
        }

        public Service build() {
            return new Service(this.id, this.categoryId, this.name, this.status, this.category, this.providerSkills, this.serviceRequests);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "CategoryId is required")
    @Min(value = 1, message = "CategoryId must be a valid value")
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @NotBlank(message = "Service name is required")
    @Size(min = 3, max = 150, message = "Service name must be between 3 and 150 characters")
    @Column(nullable = false, length = 150)
    private String name;

    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")

    @Column(nullable = false)
    private String status = "ACTIVE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @JsonIgnore
    private ServiceCategory category;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private java.util.List<ProviderSkill> providerSkills;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private java.util.List<ServiceRequest> serviceRequests;
}