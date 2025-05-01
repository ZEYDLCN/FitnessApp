package com.example.fitnessapp.entity; 
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "exercises") // Tablo adı
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; 

    @Column(columnDefinition = "TEXT") 
    private String description; 
    @Column(name = "target_muscle_group") 
    private String targetMuscleGroup; 

   
}
