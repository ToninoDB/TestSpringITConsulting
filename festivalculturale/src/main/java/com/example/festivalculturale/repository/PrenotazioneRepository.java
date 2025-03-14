package com.example.festivalculturale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.festivalculturale.models.Prenotazione;
import com.example.festivalculturale.models.Utente;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    // Query per mostrare lo storico delle prenotazioni di un utente
    @Query("SELECT p FROM Prenotazione p JOIN FETCH p.evento e WHERE p.utente = :utente")
    List<Prenotazione> findByUtente(Utente utente);

    // Query per eliminare la prenotazione solo se appartiene all'utente loggato
    void deleteByIdAndUtente(Long id, Utente utente);
}
