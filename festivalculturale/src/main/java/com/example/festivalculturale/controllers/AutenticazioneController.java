package com.example.festivalculturale.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.festivalculturale.models.Utente;
import com.example.festivalculturale.services.UtenteService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AutenticazioneController {
    private final UtenteService utenteService;

    @GetMapping("/registrazione")
    public String mostraFormRegstrazione(Model model) {
        model.addAttribute("utente", new Utente());
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String registrazione(@ModelAttribute Utente utente) {
        utenteService.registrazione(utente);
        return "redirect:/auth/login";
    }

    @GetMapping("/login")
    public String mostraFormLogin() {
        return "login";
    }
}
