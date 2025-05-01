package com.example.fitnessapp.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class WorkoutResponseDTO {
    private Long id;
    private LocalDate workoutDate;
    private String notes;
    private LocalDateTime createdAt;
    private Long userId; 
    private String username; 
}