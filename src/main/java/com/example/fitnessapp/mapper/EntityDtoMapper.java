package com.example.fitnessapp.mapper;

import com.example.fitnessapp.dto.WorkoutLogEntryResponseDTO;
import com.example.fitnessapp.dto.WorkoutResponseDTO;
// Gerekirse diğer DTO ve Entity importları
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.Workout;
import com.example.fitnessapp.entity.WorkoutLogEntry;
import org.springframework.stereotype.Component; 

import java.util.stream.Collectors;

@Component 
public class EntityDtoMapper {

    
    public WorkoutResponseDTO mapWorkoutToResponseDTO(Workout workout) {
        if (workout == null) return null;
        WorkoutResponseDTO dto = new WorkoutResponseDTO();
        dto.setId(workout.getId());
        dto.setWorkoutDate(workout.getWorkoutDate());
        dto.setNotes(workout.getNotes());
        dto.setCreatedAt(workout.getCreatedAt());
        if (workout.getUser() != null) {
            dto.setUserId(workout.getUser().getId());
            dto.setUsername(workout.getUser().getUsername());
        }
       
        if (workout.getLogEntries() != null) {
         
        }

        return dto;
    }

    
    public WorkoutLogEntryResponseDTO mapLogEntryToResponseDTO(WorkoutLogEntry entry) {
        if (entry == null) return null;
        WorkoutLogEntryResponseDTO dto = new WorkoutLogEntryResponseDTO();
        dto.setId(entry.getId());
        dto.setSets(entry.getSets());
        dto.setReps(entry.getReps());
        dto.setWeight(entry.getWeight());
        dto.setDurationInSeconds(entry.getDurationInSeconds());
        if (entry.getExercise() != null) {
            dto.setExerciseId(entry.getExercise().getId());
            dto.setExerciseName(entry.getExercise().getName());
        }
        
        return dto;
    }

      
}