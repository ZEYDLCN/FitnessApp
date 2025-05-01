package com.example.fitnessapp.controller;

import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.service.ExerciseService;
import jakarta.validation.Valid; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;       
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        
        Exercise createdExercise = exerciseService.createExercise(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    
    @GetMapping
    public ResponseEntity<Page<Exercise>> getAllExercises(Pageable pageable) { 
        Page<Exercise> exercisesPage = exerciseService.getAllExercises(pageable);
        return ResponseEntity.ok(exercisesPage);
        
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
         
    }

     
    @GetMapping("/name/{name}")
    public ResponseEntity<Exercise> getExerciseByName(@PathVariable String name) {
        return exerciseService.getExerciseByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
         
    }


    
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exerciseDetails) {
        
         try {
             Exercise updatedExercise = exerciseService.updateExercise(id, exerciseDetails);
             return ResponseEntity.ok(updatedExercise);
         } catch (ResourceNotFoundException e) {
             return ResponseEntity.notFound().build();
         }
    }

    // DELETE Exercise
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
      
        try {
            exerciseService.deleteExercise(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}