package com.example.festivalculturale.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.festivalculturale.models.Utente;
import com.example.festivalculturale.models.enums.Ruolo;
import com.example.festivalculturale.repository.UtenteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registrazione di un utente al sistema
     * 
     * @param utente che si registra
     * @return utente registrato e lo salva nel database
     */
    public Utente registrazione(Utente utente) {
        if (utente.getRuolo() == null || utente.getRuolo().name() == null) {
            utente.setRuolo(Ruolo.USER);
        }
        utente.setPassword(passwordEncoder.encode(utente.getPassword()));
        return utenteRepository.save(utente);
    }

    /**
     * @param email dell'utente da ricercare
     * @return l'utente con l'email corrispondente a quella inserita
     */
    public Optional<Utente> cercaPerEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

}
