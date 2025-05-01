package com.example.fitnessapp.entity; // Paketi kontrol et

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp; // Otomatik tarih için

import java.time.LocalDate; // Sadece Tarih tutmak için
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   
    @Column(nullable = false)
    private LocalDate workoutDate;

    @Column(columnDefinition = "TEXT") 
    private String notes; 

  
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "user_id", nullable = false) 
    private User user; 
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "workout",         
            cascade = CascadeType.ALL,     
            orphanRemoval = true           
    )
    private List<WorkoutLogEntry> logEntries = new ArrayList<>(); 
    public void addLogEntry(WorkoutLogEntry entry) {
        logEntries.add(entry);
        entry.setWorkout(this);
    }

    public void removeLogEntry(WorkoutLogEntry entry) {
        logEntries.remove(entry);
        entry.setWorkout(null);
    }
}