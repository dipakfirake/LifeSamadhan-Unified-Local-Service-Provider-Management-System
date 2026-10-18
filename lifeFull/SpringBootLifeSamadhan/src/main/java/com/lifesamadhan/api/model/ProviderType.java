package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "provider_types", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data

public class ProviderType {

    public ProviderType() {
    }

    public ProviderType(Long id, String name, String description, String status, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status != null ? status : "ACTIVE";
        this.createdAt = createdAt;
    }

    public static ProviderTypeBuilder builder() {
        return new ProviderTypeBuilder();
    }

    public static class ProviderTypeBuilder {
        private Long id;
        private String name;
        private String description;
        private String status;
        private LocalDateTime createdAt;

        public ProviderTypeBuilder() {
        }

        public ProviderTypeBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProviderTypeBuilder name(String name) {
            this.name = name;
            return this;
        }
        public ProviderTypeBuilder description(String description) {
            this.description = description;
            return this;
        }
        public ProviderTypeBuilder status(String status) {
            this.status = status;
            return this;
        }
        public ProviderTypeBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ProviderType build() {
            return new ProviderType(this.id, this.name, this.description, this.status, this.createdAt);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Pattern(regexp = "^(ACTIVE|INACTIVE)$", message = "Status must be ACTIVE or INACTIVE")

    @Column(nullable = false)
    private String status = "ACTIVE";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
