package com.example.festivalculturale.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.festivalculturale.models.Evento;
import com.example.festivalculturale.repository.EventoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

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
     * Cerca un evento per data
     * 
     * @param data
     * @return
     */
    public List<Evento> cercaPerData(LocalDate data) {
        return eventoRepository.findByData(data);
    }

    // Cerca un evento per range di prezzo
    public List<Evento> cercaPerPrezzo(Double prezzoMin, Double prezzoMax) {
        return eventoRepository.findByPrezzoBetween(prezzoMin, prezzoMax);
    }
}
