package com.example.festivalculturale.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.festivalculturale.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Cerca eventi per data
    @Query("SELECT e FROM Evento e WHERE e.data = :data")
    List<Evento> findByData(@Param("data") LocalDate data);

    // Cerca eventi in un range di prezzo
    @Query("SELECT e FROM Evento e WHERE e.prezzo BETWEEN :prezzoMin AND :prezzoMax")
    List<Evento> findByPrezzoBetween(@Param("prezzoMin") Double prezzoMin, @Param("prezzoMax") Double prezzoMax);
}
