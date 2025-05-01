package com.example.fitnessapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkoutLogEntryRequestDTO {

    @NotNull(message = "Exercise ID cannot be null")
    private Long exerciseId;

    @NotNull(message = "Sets cannot be null")
    @Min(value = 1, message = "Sets must be at least 1")
    private Integer sets;

    @NotNull(message = "Reps cannot be null")
    @Min(value = 1, message = "Reps must be at least 1")
    private Integer reps;

    @Min(value = 0, message = "Weight cannot be negative")
    private Double weight; // Opsiyonel

    @Min(value = 0, message = "Duration cannot be negative")
    private Integer durationInSeconds; // Opsiyonel
}