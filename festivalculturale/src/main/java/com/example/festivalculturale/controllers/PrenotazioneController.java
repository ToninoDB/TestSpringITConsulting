package com.example.festivalculturale.controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.festivalculturale.models.Prenotazione;
import com.example.festivalculturale.models.Utente;
import com.example.festivalculturale.services.PrenotazioneService;
import com.example.festivalculturale.services.UtenteService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;
    private final UtenteService utenteService;

    @PostMapping("/{eventoId}")
    public String effettuaPrenotazione(
            @PathVariable Long eventoId,
            @RequestParam int numeroBiglietti,
            @AuthenticationPrincipal UserDetails userDetails, Model model) {

        Utente utente = utenteService.cercaPerEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utente non trovato con email: " + userDetails.getUsername()));

        Prenotazione prenotazione = prenotazioneService.effettuaPrenotazione(eventoId, numeroBiglietti, utente);
        model.addAttribute("prenotazione", prenotazione);
        model.addAttribute("successMessage", "Prenotazione effettuata con successo!");

        return "storico";
    }

    @DeleteMapping("/{prenotazioneId}")
    public String cancellaPrenotazione(
            @PathVariable Long prenotazioneId,
            @AuthenticationPrincipal UserDetails userDetails, Model model) {

        Utente utente = utenteService.cercaPerEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utente non trovato con email: " + userDetails.getUsername()));

        prenotazioneService.cancellaPrenotazione(prenotazioneId, utente);

        List<Prenotazione> prenotazioni = prenotazioneService.mostraStorico(utente);
        model.addAttribute("prenotazioni", prenotazioni);
        model.addAttribute("successMessage", "Prenotazione eliminata con successo!");

        return "storioc";
    }

    @GetMapping("/storico")
    public String getStoricoPrenotazioni(
            @AuthenticationPrincipal UserDetails userDetails, Model model) {

        Utente utente = utenteService.cercaPerEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Utente non trovato con email: " + userDetails.getUsername()));

        List<Prenotazione> prenotazioni = prenotazioneService.mostraStorico(utente);
        model.addAttribute("prenotazioni", prenotazioni);

        return "storico";
    }
}
