package com.example.festivalculturale.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.festivalculturale.models.Evento;
import com.example.festivalculturale.models.Prenotazione;
import com.example.festivalculturale.models.Utente;
import com.example.festivalculturale.repository.EventoRepository;
import com.example.festivalculturale.repository.PrenotazioneRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final EventoRepository eventoRepository;

    /**
     * Mostra lo storico di prenotazioni di un utente
     * 
     * @param utente
     * @return
     */
    public List<Prenotazione> mostraStoricoPrenotazioni(Utente utente) {
        return prenotazioneRepository.findByUtente(utente);
    }

    /**
     * Effettua prenotazione ad un evento
     * 
     * @param eventoId
     * @param numeroBiglietti
     * @param utente
     * @return
     */
    @Transactional
    public Prenotazione effettuaPrenotazione(Long eventoId, int numeroBiglietti, Utente utente) {

        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento non trovato"));

        if (numeroBiglietti <= 0) {
            throw new IllegalArgumentException("Il numero di biglietti deve essere maggiore di 0");
        }

        Prenotazione prenotazione = Prenotazione.builder()
                .utente(utente)
                .evento(evento)
                .numeroBiglietti(numeroBiglietti)
                .dataPrenotazione(LocalDate.now())
                .build();

        return prenotazioneRepository.save(prenotazione);
    }

    /**
     * Cancella una prenotazione
     * 
     * @param prenotazioneId
     * @param utente
     */
    @Transactional
    public void cancellaPrenotazione(Long prenotazioneId, Utente utente) {

        // ✅ Controlla se la prenotazione esiste e appartiene all'utente loggato
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

        if (!prenotazione.getUtente().getId().equals(utente.getId())) {
            throw new SecurityException("Non sei autorizzato a cancellare questa prenotazione");
        }

        prenotazioneRepository.delete(prenotazione);
    }

    /**
     * Mostra tutte le prenotazioni
     * 
     * @return
     */
    public List<Prenotazione> mostraTuttePrenotazioni() {
        return prenotazioneRepository.findAll();
    }

}
