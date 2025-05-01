package com.example.fitnessapp.repository; // Paketi kontrol et

import com.example.fitnessapp.entity.Workout; // Doğru entity'yi import et
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;       // Page import et
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    // Belirli bir kullanıcıya ait tüm antrenmanları bul
    Page<Workout> findByUserId(Long userId, Pageable pageable);

    // Belirli bir kullanıcının belirli bir tarihteki antrenmanlarını bul (Gün 6'da geliştirilecek)
    List<Workout> findByUserIdAndWorkoutDate(Long userId, LocalDate date);

    // Belirli bir kullanıcının tarih aralığındaki antrenmanları (Gün 6'da)
    // List<Workout> findByUserIdAndWorkoutDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
}