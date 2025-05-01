package com.example.fitnessapp.repository;

import com.example.fitnessapp.entity.WorkoutLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutLogEntryRepository extends JpaRepository<WorkoutLogEntry, Long> {

   
    List<WorkoutLogEntry> findByWorkoutId(Long workoutId);

    
}