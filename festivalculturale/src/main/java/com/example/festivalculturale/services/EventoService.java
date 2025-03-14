package com.example.festivalculturale.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.festivalculturale.models.Artista;
import com.example.festivalculturale.models.Evento;
import com.example.festivalculturale.models.Sede;
import com.example.festivalculturale.repository.EventoRepository;
import com.example.festivalculturale.repository.PrenotazioneRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final PrenotazioneRepository prenotazioneRepository;

    /**
     * @return la lista di tutti gli eventi presenti
     */
    public List<Evento> mostraTutti() {
        return eventoRepository.findAll();
    }

    /**
     * Salva un nuovo evento
     * 
     * @param evento
     * @return
     */
    public Evento salvaEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    /**
     * Cerca un evento per id
     * 
     * @param id
     * @return
     */
    public Optional<Evento> cercaPerId(Long id) {
        return eventoRepository.findById(id);
    }

    /**
     * Aggiorna un evento completamente
     * 
     * @param id
     * @param evento
     * @return
     */
    public Evento aggiornaEvento(Long id, Evento evento) {
        Evento esistente = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato con ID: " + id));

        esistente.setTitolo(evento.getTitolo());
        esistente.setData(evento.getData());
        esistente.setPrezzo(evento.getPrezzo());

        return eventoRepository.save(esistente);
    }

    /**
     * Elimina un evento per id
     * 
     * @param id
     */
    public void elimina(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato con ID: " + id));
        eventoRepository.delete(evento);
    }

    /**
     * Cerca gli eventi per data
     * 
     * @param data
     * @return
     */
    public List<Evento> cercaPerData(LocalDate data) {
        return eventoRepository.findByData(data);
    }

    /**
     * Cerca tutti gli eventi per range di prezzo
     * 
     * @param prezzoMin
     * @param prezzoMax
     * @return
     */
    public List<Evento> cercaPerPrezzo(Double prezzoMin, Double prezzoMax) {
        return eventoRepository.findByPrezzoBetween(prezzoMin, prezzoMax);
    }

    /**
     * Cerca tutti gli eventi a cui partecipa un artista
     * 
     * @param artista
     * @return
     */
    public List<Evento> cercaPerArtista(Artista artista) {
        return eventoRepository.findByArtistiContaining(artista);
    }

    /**
     * Cerca gli eventi in una sede
     * 
     * @param sede
     * @return
     */
    public Evento cercaPerSede(Sede sede) {
        return eventoRepository.findBySede(sede);
    }

    /**
     * Calcola il numero di bigletti venduti per un evento
     * 
     * @param evento
     * @return
     */
    public int numeroBigliettiVenduti(Evento evento) {
        Integer biglietti = prenotazioneRepository.getBigliettiTotali(evento);
        return biglietti != null ? biglietti : 0;
    }
}
