package com.lifesamadhan.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Data

public class Rating {

    public Rating() {
    }

    public Rating(Long id, Long assignmentId, Integer stars, String feedback, LocalDateTime createdAt, ServiceAssignment assignment) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.stars = stars;
        this.feedback = feedback;
        this.createdAt = createdAt;
        this.assignment = assignment;
    }

    public static RatingBuilder builder() {
        return new RatingBuilder();
    }

    public static class RatingBuilder {
        private Long id;
        private Long assignmentId;
        private Integer stars;
        private String feedback;
        private LocalDateTime createdAt;
        private ServiceAssignment assignment;

        public RatingBuilder() {
        }

        public RatingBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public RatingBuilder assignmentId(Long assignmentId) {
            this.assignmentId = assignmentId;
            return this;
        }
        public RatingBuilder stars(Integer stars) {
            this.stars = stars;
            return this;
        }
        public RatingBuilder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }
        public RatingBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public RatingBuilder assignment(ServiceAssignment assignment) {
            this.assignment = assignment;
            return this;
        }

        public Rating build() {
            return new Rating(this.id, this.assignmentId, this.stars, this.feedback, this.createdAt, this.assignment);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "AssignmentId is required")
    @Min(value = 1, message = "AssignmentId must be a valid value")
    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @NotNull(message = "Stars rating is required")
    @Min(value = 1, message = "Rating must be at least 1 star")
    @Max(value = 5, message = "Rating cannot exceed 5 stars")
    @Column(nullable = false)
    private Integer stars;

    @Size(max = 500, message = "Feedback cannot exceed 500 characters")
    @Column(length = 500)
    private String feedback;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", insertable = false, updatable = false)
    private ServiceAssignment assignment;
}