package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.WorkoutLogEntryRequestDTO;
import com.example.fitnessapp.dto.WorkoutLogEntryResponseDTO;
import com.example.fitnessapp.dto.WorkoutResponseDTO;
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.Workout;
import com.example.fitnessapp.entity.WorkoutLogEntry;
import com.example.fitnessapp.exception.ResourceNotFoundException;
import com.example.fitnessapp.mapper.EntityDtoMapper; // Mapper import edildi
import com.example.fitnessapp.repository.ExerciseRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.repository.WorkoutLogEntryRepository;
import com.example.fitnessapp.repository.WorkoutRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;        // Page import
import org.springframework.data.domain.Pageable;   // Pageable import
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutLogEntryRepository workoutLogEntryRepository;
    private final EntityDtoMapper entityDtoMapper; // Mapper inject edildi

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository,
                              UserRepository userRepository,
                              ExerciseRepository exerciseRepository,
                              WorkoutLogEntryRepository workoutLogEntryRepository,
                              EntityDtoMapper entityDtoMapper) { 
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
        this.workoutLogEntryRepository = workoutLogEntryRepository;
        this.entityDtoMapper = entityDtoMapper; 
    }

    @Override
    @Transactional
    
    public WorkoutResponseDTO createWorkout(Workout workoutRequestData, Long userId) {
        log.info("Attempting to create workout for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {} when trying to create workout.", userId);
                    return new ResourceNotFoundException("User", "id", userId);
                 });

        Workout newWorkout = new Workout();
        newWorkout.setWorkoutDate(workoutRequestData.getWorkoutDate());
        newWorkout.setNotes(workoutRequestData.getNotes());
        newWorkout.setUser(user);

        Workout savedWorkout = workoutRepository.save(newWorkout);
        log.info("Workout created successfully with ID: {}", savedWorkout.getId());
       
        return entityDtoMapper.mapWorkoutToResponseDTO(savedWorkout);
    }

    @Override
    @Transactional
   
    public WorkoutResponseDTO updateWorkout(Long workoutId, Workout workoutDetails) {
        log.info("Updating workout with ID: {}", workoutId);
        Workout existingWorkout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> {
                     log.error("Workout not found for update with ID: {}", workoutId);
                     return new ResourceNotFoundException("Workout", "id", workoutId);
                 });

        existingWorkout.setWorkoutDate(workoutDetails.getWorkoutDate());
        existingWorkout.setNotes(workoutDetails.getNotes());

        Workout updatedWorkout = workoutRepository.save(existingWorkout);
        log.info("Workout updated successfully: {}", updatedWorkout.getId());
      
        return entityDtoMapper.mapWorkoutToResponseDTO(updatedWorkout);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutResponseDTO> getWorkoutById(Long workoutId) {
        log.debug("Fetching workout by ID: {}", workoutId);
        return workoutRepository.findById(workoutId)
                                
                                .map(entityDtoMapper::mapWorkoutToResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
   
    public Page<WorkoutResponseDTO> getWorkoutsByUserId(Long userId, Pageable pageable) {
        log.debug("Fetching workouts for user ID: {} with pagination/sorting: {}", userId, pageable);
        Page<Workout> workoutsPage = workoutRepository.findByUserId(userId, pageable);
        log.debug("Found {} workouts for user {} on page {}/{}", workoutsPage.getNumberOfElements(), userId, pageable.getPageNumber(), workoutsPage.getTotalPages());
      
        return workoutsPage.map(entityDtoMapper::mapWorkoutToResponseDTO);
    }

    @Override
    @Transactional
 
    public WorkoutLogEntryResponseDTO addLogEntryToWorkout(Long workoutId, WorkoutLogEntryRequestDTO entryDto) {
        log.info("Adding exercise entry to workout ID: {}", workoutId);

        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout", "id", workoutId));

        Exercise exercise = exerciseRepository.findById(entryDto.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", entryDto.getExerciseId()));

        WorkoutLogEntry newEntry = new WorkoutLogEntry();
        newEntry.setWorkout(workout);
        newEntry.setExercise(exercise);
        newEntry.setSets(entryDto.getSets());
        newEntry.setReps(entryDto.getReps());
        newEntry.setWeight(entryDto.getWeight());
        newEntry.setDurationInSeconds(entryDto.getDurationInSeconds());

        WorkoutLogEntry savedEntry = workoutLogEntryRepository.save(newEntry);
        log.info("WorkoutLogEntry created with ID: {}", savedEntry.getId());

        // Inject edilen mapper'ı kullan
        return entityDtoMapper.mapLogEntryToResponseDTO(savedEntry);
    }

   
    @Override
    @Transactional
    public void deleteWorkout(Long workoutId) {
        log.warn("Attempting to delete workout with ID: {}", workoutId);
        Workout workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> {
                     log.error("Workout not found for deletion with ID: {}", workoutId);
                     return new ResourceNotFoundException("Workout", "id", workoutId);
                 });
        workoutRepository.delete(workout);
        log.info("Workout deleted successfully with ID: {}", workoutId);
    }
}