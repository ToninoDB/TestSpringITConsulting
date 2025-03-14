# 🎟️ Sistema di Prenotazione Eventi

## 📖 Descrizione

Questa applicazione è un sistema di prenotazione eventi basato su **Spring Boot**, che consente agli utenti di:

- Registrarsi e autenticarsi
- Visualizzare la lista degli eventi disponibili
- Effettuare prenotazioni
- Gestire il proprio storico prenotazioni
- Gli amministratori possono gestire eventi e prenotazioni tramite un pannello di controllo

---

## 🏛️ **Struttura del Progetto**

```
src
├── main
│   ├── java
│   │   └── com.example.festivalculturale
│   │       ├── controllers
│   │       │   ├── AdminController.java
│   │       │   ├── EventoController.java
│   │       │   ├── PrenotazioneController.java
│   │       │   └── AutenticazioneController.java
│   │       ├── models
│   │       │   ├── Evento.java
│   │       │   ├── Prenotazione.java
│   │       │   └── Utente.java
│   │       ├── repository
│   │       │   ├── EventoRepository.java
│   │       │   ├── PrenotazioneRepository.java
│   │       │   └── UtenteRepository.java
│   │       ├── services
│   │       │   ├── EventoService.java
│   │       │   ├── PrenotazioneService.java
│   │       │   └── UtenteService.java
│   │       └── security
│   │           └── SecurityConfig.java
│   └── resources
│       ├── templates
│       │   ├── admin
│       │   │   ├── dashboard.html
│       │   │   ├── eventi.html
│       │   │   ├── formEvento.html
│       │   │   ├── prenotazioni.html
│       │   ├── autenticazione
│       │   │   ├── login.html
│       │   │   ├── registrazione.html
│       │   └── utente
│       │       ├── storico.html
│       └── application.properties
├── test
└── README.md
```

---

## 🗃️ **Diagramma ER**

```mermaid
erDiagram
    UTENTE {
        Long id PK
        String nome
        String cognome
        String email UNIQUE
        String password
        ENUM ruolo (USER, ADMIN)
    }

    EVENTO {
        Long id PK
        String titolo
        String descrizione
        LocalDate data
        Double prezzo
    }

    PRENOTAZIONE {
        Long id PK
        LocalDate dataPrenotazione
        Integer numeroBiglietti
        FK utente_id
        FK evento_id
    }

    UTENTE ||--o{ PRENOTAZIONE : fa
    EVENTO ||--o{ PRENOTAZIONE : contiene
```

---

## ✨ **Funzionalità Implementate**

### ✅ **Utente:**

- [x] Registrazione e login
- [x] Visualizzazione eventi disponibili
- [x] Prenotazione di eventi
- [x] Gestione storico prenotazioni

### ✅ **Admin:**

- [x] Dashboard di controllo
- [x] Creazione di nuovi eventi
- [x] Modifica degli eventi
- [x] Eliminazione degli eventi
- [x] Visualizzazione di tutte le prenotazioni
- [x] Ricerca eventi per prezzo e data
- [x] Conteggio totale prenotazioni per evento

---

## 💾 **Tecnologie Utilizzate**

| Tecnologia          | Versione | Scopo                              |
| ------------------- | -------- | ---------------------------------- |
| **Java**            | 17       | Linguaggio di programmazione       |
| **Spring Boot**     | 3.1.3    | Framework di backend               |
| **Spring Security** | 6.x      | Autenticazione e sicurezza         |
| **Spring Data JPA** | 3.x      | ORM e gestione database            |
| **Thymeleaf**       | 3.1.x    | Template engine per rendering HTML |
| **MySQL**           | 8.x      | Database relazionale               |
| **Lombok**          | 1.18.x   | Generazione automatica di codice   |
| **BCrypt**          | 0.9.x    | Encoding delle password            |
| **H2 Database**     | 1.x      | Database in memoria per test       |

---
