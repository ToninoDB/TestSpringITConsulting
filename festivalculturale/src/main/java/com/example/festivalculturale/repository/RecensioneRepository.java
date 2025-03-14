package com.example.festivalculturale.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.festivalculturale.models.Evento;
import com.example.festivalculturale.models.Recensione;
import com.example.festivalculturale.models.Utente;

public interface RecensioneRepository extends JpaRepository<Recensione, Long> {
    // Query per mostrare lo storico delle recensioni di un utente
    @Query("SELECT r FROM Recensione r JOIN FETCH r.evento e WHERE r.utente = :utente")
    List<Recensione> findByUtente(Utente utente);

    // Query per controllare se un utente ha già recensito un evento
    boolean existsByUtenteAndEvento(Utente utente, Evento evento);

    // Query per calcolare la valutazione media di un evento
    @Query("SELECT AVG(r.valutazione) FROM Recensione r WHERE r.evento = :evento")
    Double getValutazioneMedia(Evento evento);

}
