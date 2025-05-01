package com.example.fitnessapp.dto;

import lombok.Data;

@Data
public class WorkoutLogEntryResponseDTO {
    private Long id;
    private Long exerciseId;
    private String exerciseName; 
    private Integer sets;
    private Integer reps;
    private Double weight;
    private Integer durationInSeconds;
    
}