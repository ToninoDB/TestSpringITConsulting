package com.example.festivalculturale.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.festivalculturale.models.Evento;
import com.example.festivalculturale.models.Recensione;
import com.example.festivalculturale.models.Utente;
import com.example.festivalculturale.repository.PrenotazioneRepository;
import com.example.festivalculturale.repository.RecensioneRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecensioneService {
    private final RecensioneRepository recensioneRepository;
    private final PrenotazioneRepository prenotazioneRepository;

    /**
     * Mostra storico recensioni dell'utente
     * 
     * @param utente
     * @return
     */
    public List<Recensione> mostraStoricoRecensioni(Utente utente) {
        return recensioneRepository.findByUtente(utente);
    }

    /**
     * Mostra tutte le recensioni
     * 
     * @return
     */
    public List<Recensione> mostraTutteRecensioni() {
        return recensioneRepository.findAll();
    }

    /**
     * Aggiunta di una recensione ad un evento a cui l'utente ha già partecipato
     * 
     * @param utente
     * @param evento
     * @param valutazione
     * @param commento
     * @return
     */
    @Transactional
    public Recensione aggiungiRecensione(Utente utente, Evento evento, int valutazione, String commento) {
        // Controlla se l'utente ha partecipato all'evento
        boolean haPartecipato = prenotazioneRepository.existsByUtenteAndEvento(utente, evento);

        if (!haPartecipato) {
            throw new IllegalArgumentException("L'utente non ha partecipato a questo evento");
        }

        // Controlla se l'utente ha già recensito l'evento
        if (recensioneRepository.existsByUtenteAndEvento(utente, evento)) {
            throw new IllegalArgumentException("Hai già recensito questo evento");
        }

        Recensione review = Recensione.builder()
                .utente(utente)
                .evento(evento)
                .valutazione(valutazione)
                .commento(commento)
                .build();

        return recensioneRepository.save(review);
    }

    /**
     * Cancella recensione
     * 
     * @param id
     */
    @Transactional
    public void cancellaRecensione(Long id) {
        Recensione recensione = recensioneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recensione non trovata"));
        recensioneRepository.delete(recensione);
    }

    /**
     * Calcola valutazione media per evento
     * 
     * @param evento
     * @return
     */
    public double calcolaValutazioneMedia(Evento evento) {
        Double media = recensioneRepository.getValutazioneMedia(evento);
        return media != null ? media : 0.0;
    }
}
