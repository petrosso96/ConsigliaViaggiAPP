package com.example.consigliaviaggi.model;

public class LikesUtenti {

    private String nomeUtente;
    private Recensione recensione;

    public LikesUtenti() {
    }

    public LikesUtenti(String nomeUtente,Recensione recensione) {

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