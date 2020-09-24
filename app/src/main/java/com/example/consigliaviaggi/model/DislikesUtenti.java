package com.example.consigliaviaggi.model;

public class DislikesUtenti {

    private String nomeUtente;
    private Recensione recensione;

    public DislikesUtenti() {
    }

    public DislikesUtenti(String nomeUtente, Recensione recensione) {

        this.nomeUtente = nomeUtente;
        this.recensione = recensione;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }
}