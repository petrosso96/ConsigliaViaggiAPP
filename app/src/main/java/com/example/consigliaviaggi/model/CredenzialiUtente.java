package com.example.consigliaviaggi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CredenzialiUtente {


    private String nomeUtente;


    private String password;

    public CredenzialiUtente() {
    }

    public CredenzialiUtente(String nomeUtente, String password){
        this.nomeUtente = nomeUtente;
        this.password = password;
    }

    @JsonProperty("username")
    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
