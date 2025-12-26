package com.keskin.workouts.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workouts")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "workout_name", nullable = false)
    private String workoutName;

    private String category;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutItem> items;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}