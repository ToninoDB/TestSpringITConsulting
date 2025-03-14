package com.example.festivalculturale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.festivalculturale.models.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Optional<Utente> findByEmail(String email);
}
