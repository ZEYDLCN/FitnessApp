package com.example.fitnessapp.controller;

import com.example.fitnessapp.dto.WorkoutLogEntryRequestDTO;
import com.example.fitnessapp.dto.WorkoutLogEntryResponseDTO;
import com.example.fitnessapp.dto.WorkoutResponseDTO;
import com.example.fitnessapp.entity.Workout;
import com.example.fitnessapp.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("/{workoutId}/entries")
    public ResponseEntity<WorkoutLogEntryResponseDTO> addLogEntryToWorkout(
            @PathVariable Long workoutId,
            @Valid @RequestBody WorkoutLogEntryRequestDTO entryDto) {

        WorkoutLogEntryResponseDTO createdEntryDTO = workoutService.addLogEntryToWorkout(workoutId, entryDto);
        return new ResponseEntity<>(createdEntryDTO, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<WorkoutResponseDTO> createWorkout(
            @RequestBody Workout workoutRequestData,
            @RequestParam Long userId) {

        WorkoutResponseDTO createdWorkoutDTO = workoutService.createWorkout(workoutRequestData, userId);
        return new ResponseEntity<>(createdWorkoutDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDTO> getWorkoutById(@PathVariable Long workoutId) {
        return workoutService.getWorkoutById(workoutId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<WorkoutResponseDTO>> getWorkoutsByUserId(
            @RequestParam Long userId,
            Pageable pageable) {

        Page<WorkoutResponseDTO> workoutsPage = workoutService.getWorkoutsByUserId(userId, pageable);
        return ResponseEntity.ok(workoutsPage);
    }

    @PutMapping("/{workoutId}")
    public ResponseEntity<WorkoutResponseDTO> updateWorkout(
            @PathVariable Long workoutId,
            @RequestBody Workout workoutDetails) {

        WorkoutResponseDTO updatedWorkoutDTO = workoutService.updateWorkout(workoutId, workoutDetails);
        return ResponseEntity.ok(updatedWorkoutDTO);
    }

    @DeleteMapping("/{workoutId}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long workoutId) {
        workoutService.deleteWorkout(workoutId);
        return ResponseEntity.noContent().build();
    }
}
