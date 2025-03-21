package com.example.festivalculturale.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.festivalculturale.models.Evento;
import com.example.festivalculturale.models.Prenotazione;
import com.example.festivalculturale.models.Utente;
import com.example.festivalculturale.services.EventoService;
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
        private final EventoService eventoService;

        @GetMapping("/nuova/{eventoId}")
        public String mostraFormPrenotazione(@PathVariable Long eventoId, Model model) {
                model.addAttribute("eventoId", eventoId);
                return "nuovaPrenotazione";
        }

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

                return "storico";
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

        @GetMapping("/ricerca/data")
        public String cercaPerData(@RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
                        Model model) {
                List<Evento> eventi = eventoService.cercaPerData(data);
                model.addAttribute("eventi", eventi);
                return "eventi";
        }

        @GetMapping("/ricerca/prezzo")
        public String cercaPerPrezzo(@RequestParam("prezzoMin") Double prezzoMin,
                        @RequestParam("prezzoMax") Double prezzoMax,
                        Model model) {
                List<Evento> eventi = eventoService.cercaPerPrezzo(prezzoMin, prezzoMax);
                model.addAttribute("eventi", eventi);
                return "eventi";
        }

        @GetMapping("/mostraEventi")
        public String mostraEventiDisponibili(Model model) {
                List<Evento> listaEventi = eventoService.mostraTutti();
                model.addAttribute("eventi", listaEventi);
                return "eventi";
        }

        @GetMapping("/dashboard")
        public String dashboardUtente() {
                return "dashboardUtente";
        }

}
