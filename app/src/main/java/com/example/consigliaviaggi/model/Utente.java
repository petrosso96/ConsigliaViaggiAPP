package com.example.consigliaviaggi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Utente {

    public Utente() {
    }

    public Utente(String nomeUtente, String nome, String cognome, String indirizzoEmail, String password, Gender sesso, String city, Date dataDiNascita) {
        this.nomeUtente = nomeUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzoEmail = indirizzoEmail;
        this.password = password;
        this.sesso = sesso;
        this.city = city;
        this.dataDiNascita = dataDiNascita;

    }

    private String nome;
    private String cognome;
    private String nomeUtente;
    private Date dataDiNascita;
    private String password;
    private String city;
    private String indirizzoEmail;
    private Gender sesso;
    private Rank rank;
    private MostraCome mostraCome;
    @JsonProperty("active")
    private boolean isActive;
    @JsonProperty("utente")
    private String utente;


    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @JsonProperty("cognome")
    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }



    @JsonProperty("nomeUtente")
    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    @JsonProperty("dataDiNascita")
    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("indirizzoEmail")
    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    public void setIndirizzoEmail(String indirizzoEmail) {
        this.indirizzoEmail = indirizzoEmail;
    }

    @JsonProperty("sesso")
    public Gender getSesso() {
        return sesso;
    }

    public void setSesso(Gender sesso) {
        this.sesso = sesso;
    }

    @JsonProperty("rank")
    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    @JsonProperty("mostraCome")
    public MostraCome getMostraCome() {
        return mostraCome;
    }

    public void setMostraCome(MostraCome mostraCome) {
        this.mostraCome = mostraCome;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", nomeUtente='" + nomeUtente + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", password='" + password + '\'' +
                ", city='" + city + '\'' +
                ", indirizzoEmail='" + indirizzoEmail + '\'' +
                ", sesso=" + sesso +
                '}';
    }
}
