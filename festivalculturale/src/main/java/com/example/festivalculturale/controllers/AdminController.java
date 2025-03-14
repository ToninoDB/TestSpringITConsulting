package com.example.festivalculturale.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.festivalculturale.models.Evento;
import com.example.festivalculturale.models.Prenotazione;
import com.example.festivalculturale.services.EventoService;
import com.example.festivalculturale.services.PrenotazioneService;
import com.example.festivalculturale.services.RecensioneService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final EventoService eventoService;
    private final PrenotazioneService prenotazioneService;
    private final RecensioneService recensioneService;

    @GetMapping("/eventi")
    public String listaEventi(Model model) {
        List<Evento> eventi = eventoService.mostraTutti();
        model.addAttribute("eventi", eventi);
        return "admin/eventi";
    }

    @GetMapping("/eventi/nuovo")
    public String mostraFormNuovoEvento(Model model) {
        model.addAttribute("evento", new Evento());
        return "admin/formEvento";
    }

    @PostMapping("/eventi")
    public String creaEvento(@ModelAttribute Evento evento) {
        eventoService.salvaEvento(evento);
        return "redirect:/admin/eventi";
    }

    @GetMapping("/eventi/modifica/{id}")
    public String mostraFormModificaEvento(@PathVariable Long id, Model model) {
        Evento evento = eventoService.cercaPerId(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento non trovato con ID: " + id));
        model.addAttribute("evento", evento);
        return "admin/formEvento";
    }

    @PostMapping("/eventi/modifica/{id}")
    public String modificaEvento(@PathVariable Long id, @ModelAttribute Evento evento) {
        eventoService.aggiornaEvento(id, evento);
        return "redirect:/admin/eventi";
    }

    @PostMapping("/eventi/elimina/{id}")
    public String eliminaEvento(@PathVariable Long id) {
        eventoService.elimina(id);
        return "redirect:/admin/eventi";
    }

    @GetMapping("/prenotazioni")
    public String listaPrenotazioni(Model model) {
        List<Prenotazione> prenotazioni = prenotazioneService.mostraTuttePrenotazioni();
        model.addAttribute("prenotazioni", prenotazioni);
        return "admin/prenotazioni";
    }
<<<<<<< Updated upstream
=======

    @GetMapping("/dashboard")
    public String dashboardAdmin() {
        return "admin/dashboardAdmin";
    }

    @GetMapping("/dettagli-evento")
    public String getEventDetails(@PathVariable Long id, Model model) {
        Optional<Evento> appoggio = eventoService.cercaPerId(id);
        Evento evento = appoggio.get();
        double valutazioneMedia = recensioneService.calcolaValutazioneMedia(evento);
        int bigliettiVenduti = eventoService.numeroBigliettiVenduti(evento);

        model.addAttribute("evento", evento);
        model.addAttribute("valutazioneMedia", valutazioneMedia);
        model.addAttribute("bigliettiVenduti", bigliettiVenduti);

        return "admin/dettagliEventi";
    }
>>>>>>> Stashed changes
}
