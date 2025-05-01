package com.example.fitnessapp.service; // Paketi kontrol et

import com.example.fitnessapp.dto.WorkoutLogEntryRequestDTO;
import com.example.fitnessapp.dto.WorkoutLogEntryResponseDTO; // <-- YENİ IMPORT
import com.example.fitnessapp.dto.WorkoutResponseDTO;
import com.example.fitnessapp.entity.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface WorkoutService {

   
    WorkoutResponseDTO createWorkout(Workout workout, Long userId);

    
    Optional<WorkoutResponseDTO> getWorkoutById(Long workoutId);

    
    Page<WorkoutResponseDTO> getWorkoutsByUserId(Long userId, Pageable pageable);

    WorkoutResponseDTO updateWorkout(Long workoutId, Workout workoutDetails);

  
    void deleteWorkout(Long workoutId);

   
    WorkoutLogEntryResponseDTO addLogEntryToWorkout(Long workoutId, WorkoutLogEntryRequestDTO entryDto);
}