package com.lifesamadhan.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Entity
@Table(name = "service_categories")
@Data

public class ServiceCategory {

    public ServiceCategory() {
    }

    public ServiceCategory(Long id, String name, String status, List<Service> services, List<ServiceProvider> providers) {
        this.id = id;
        this.name = name;
        this.status = status != null ? status : "ACTIVE";
        this.services = services;
        this.providers = providers;
    }

    public static ServiceCategoryBuilder builder() {
        return new ServiceCategoryBuilder();
    }

    public static class ServiceCategoryBuilder {
        private Long id;
        private String name;
        private String status;
        private List<Service> services;
        private List<ServiceProvider> providers;

        public ServiceCategoryBuilder() {
        }

        public ServiceCategoryBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceCategoryBuilder name(String name) {
            this.name = name;
            return this;
        }
        public ServiceCategoryBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ServiceCategoryBuilder services(List<Service> services) {
            this.services = services;
            return this;
        }
        public ServiceCategoryBuilder providers(List<ServiceProvider> providers) {
            this.providers = providers;
            return this;
        }

        public ServiceCategory build() {
            return new ServiceCategory(this.id, this.name, this.status, this.services, this.providers);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 100, message = "Category name must be between 3 and 100 characters")
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")

    @Column(nullable = false)
    private String status = "ACTIVE";

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Service> services;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<ServiceProvider> providers;
}