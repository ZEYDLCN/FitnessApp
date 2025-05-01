package com.example.fitnessapp.service; // Paket adını kontrol et

import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.exception.ResourceNotFoundException; // Bu exception sınıfı olmalı
import com.example.fitnessapp.repository.ExerciseRepository;
import lombok.extern.slf4j.Slf4j; // Loglama için
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j // Loglamayı ekleyelim
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Autowired
    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    @Transactional
    public Exercise createExercise(Exercise exercise) {
        log.info("Creating new exercise: {}", exercise.getName());
        // İleride: Aynı isimde egzersiz var mı kontrolü eklenebilir
        return exerciseRepository.save(exercise);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Exercise> getExerciseById(Long id) {
        log.debug("Fetching exercise by ID: {}", id);
        return exerciseRepository.findById(id);
    }

     @Override
    @Transactional(readOnly = true)
    public Optional<Exercise> getExerciseByName(String name) {
        log.debug("Fetching exercise by name: {}", name);
        return exerciseRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exercise> getAllExercises(Pageable pageable) {
        log.debug("Fetching all exercises with pagination/sorting: {}", pageable);
        // Repository'nin findAll(Pageable) metodunu kullan
        Page<Exercise> exercisePage = exerciseRepository.findAll(pageable);
        log.debug("Found {} exercises on page {}/{}", exercisePage.getNumberOfElements(), pageable.getPageNumber(), exercisePage.getTotalPages());
        // TODO: Gün 5'te ExerciseResponseDTO olsaydı, burada page.map(...) ile dönüşüm yapardık.
        // Şimdilik direkt Page<Exercise> döndürüyoruz.
        return exercisePage;
    }
    @Override
    @Transactional
    public Exercise updateExercise(Long id, Exercise exerciseDetails) {
        log.info("Updating exercise with ID: {}", id);
        Exercise existingExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> {
                     log.error("Exercise not found for update with ID: {}", id);
                     return new ResourceNotFoundException("Exercise", "id", id);
                 });

        existingExercise.setName(exerciseDetails.getName());
        existingExercise.setDescription(exerciseDetails.getDescription());
        existingExercise.setTargetMuscleGroup(exerciseDetails.getTargetMuscleGroup());

        Exercise updatedExercise = exerciseRepository.save(existingExercise);
        log.info("Exercise updated successfully: {}", updatedExercise.getId());
        return updatedExercise;
    }

    @Override
    @Transactional
    public void deleteExercise(Long id) {
        log.warn("Attempting to delete exercise with ID: {}", id); // Silme işlemi için WARN olabilir
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Exercise not found for deletion with ID: {}", id);
                    return new ResourceNotFoundException("Exercise", "id", id);
                 });
        exerciseRepository.delete(exercise);
        log.info("Exercise deleted successfully with ID: {}", id);
    }
}