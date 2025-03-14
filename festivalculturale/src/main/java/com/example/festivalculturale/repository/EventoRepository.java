package com.example.festivalculturale.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalculturale.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
