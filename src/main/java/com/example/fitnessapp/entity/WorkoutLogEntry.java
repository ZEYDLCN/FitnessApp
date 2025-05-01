package com.example.fitnessapp.entity; 

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "workout_log_entries") // Tablo adı
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "workout_id", nullable = false) 
    private Workout workout;

  
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private Integer sets; 
    @Column(nullable = false)
    private Integer reps; 

    private Double weight; 

    private Integer durationInSeconds; 

    
}