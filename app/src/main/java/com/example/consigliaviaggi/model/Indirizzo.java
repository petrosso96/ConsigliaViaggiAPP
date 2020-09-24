package com.example.consigliaviaggi.model;

import java.util.Objects;

public class Indirizzo {

    private String id;
    private String via;
    private Integer civico;
    private String city;

    public Indirizzo(){}

    public Indirizzo(String via, Integer civico, String city){
        this.via = via;
        this.civico = civico;
        this.city = city;
        this.id =via+"-"+civico+"-"+city;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public Integer getCivico() {
        return civico;
    }

    public void setCivico(Integer civico) {
        this.civico = civico;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Indirizzo{" +
                "id=" + id +
                ", via='" + via + '\'' +
                ", civico=" + civico +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indirizzo indirizzo = (Indirizzo) o;
        return via.equals(indirizzo.via) &&
                civico.equals(indirizzo.civico) &&
                city.equals(indirizzo.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(via, civico, city);
    }
}
