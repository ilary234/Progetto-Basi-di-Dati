package model;

import java.util.Objects;

public class Utente {
    
    private final String username; 
    private final String password; 
    private final String nome; 
    private final String cognome; 
    private final String email; 

    public Utente(String username, String password, String nome, String cognome, String email) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.nome = Objects.requireNonNull(nome);
        this.cognome = Objects.requireNonNull(cognome);
        this.email = email;
    }
}