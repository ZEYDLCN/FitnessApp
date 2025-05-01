package com.example.fitnessapp.service; 

import com.example.fitnessapp.entity.Exercise; 

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;       
import org.springframework.data.domain.Pageable;

public interface ExerciseService {
    Exercise createExercise(Exercise exercise);
    Optional<Exercise> getExerciseById(Long id);
    Optional<Exercise> getExerciseByName(String name);
    Page<Exercise> getAllExercises(Pageable pageable);
    Exercise updateExercise(Long id, Exercise exerciseDetails);
    void deleteExercise(Long id);
}