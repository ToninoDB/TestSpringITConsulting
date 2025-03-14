package com.example.festivalculturale.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titolo;
    private String descrizione;
    private LocalDate data;
    private double prezzo;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<Recensione> recensioni;

    @ManyToOne
    @JoinColumn(name = "sede_id")
    private Sede sede;

    /*
     * Per la relazione many-to-many senza una tabella di reificazione viene creata
     * una tabella di join tra le tabelle Artista ed Evento così da poter gestire il
     * caso in cui ad un evento possano partecipare più artisti e il caso in cui un
     * artista possa partecipare più eventi
     */
    @ManyToMany
    @JoinTable(name = "event_artist", joinColumns = @JoinColumn(name = "evento_id"), inverseJoinColumns = @JoinColumn(name = "artista_id"))
    private List<Artista> artisti;
}
