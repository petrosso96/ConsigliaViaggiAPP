package com.example.consigliaviaggi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Filtri {

    private String nome;
    private String city;
    private String categoria;
    private double latitudine;
    private double longitudine;
    private int distanza;
    private int prezzo;

    public Filtri(String nome, String city, String categoria, double latitudine, double longitudine, int distanza, int prezzo) {

        this.nome = nome;
        this.city = city;
        this.categoria = categoria;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.distanza = distanza;
        this.prezzo = prezzo;
    }

    @JsonProperty("nome")
    public String getNome() {
        return nome;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("categoria")
    public String getCategoria() {
        return categoria;
    }

    @JsonProperty("latitudine")
    public double getLatitudine() {
        return latitudine;
    }

    @JsonProperty("longitudine")
    public double getLongitudine() {
        return longitudine;
    }

    @JsonProperty("distanza")
    public int getDistanza() {
        return distanza;
    }

    @JsonProperty("prezzo")
    public int getPrezzo() {
        return prezzo;
    }

    @Override
    public String toString() {
        return "Filtri{" +
                "nome='" + nome + '\'' +
                ", city='" + city + '\'' +
                ", categoria='" + categoria + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", distanza=" + distanza +
                ", prezzo=" + prezzo +
                '}';
    }
}
