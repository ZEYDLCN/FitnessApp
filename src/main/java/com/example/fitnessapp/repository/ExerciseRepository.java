package com.example.fitnessapp.repository; 
import com.example.fitnessapp.entity.Exercise; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    
    Optional<Exercise> findByName(String name);
}