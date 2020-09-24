package com.example.consigliaviaggi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Recensione {

    private long id;
    private int voto;
    private String descrizione;
    private int likes =0;
    private int dislikes= 0;
    private Date dataDiAggiunta;
    private Utente autore;
    private Struttura struttura;
    private List<LikesUtenti> likesUtenti;
    private List<DislikesUtenti> dislikesUtenti;


    public Recensione() {}

    public Recensione(int voto, String descrizione) {
        this.voto = voto;
        this.descrizione = descrizione;

    }


    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public int getVoto() {
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int like) {
        this.likes = like;
    }

    public void addLike(){ this.likes++;}

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislike) {
        this.dislikes = dislike;
    }

    public void addDislike(){ this.dislikes++;}

    public Date getDataDiAggiunta() {
        return dataDiAggiunta;
    }

    public void setDataDiAggiunta(Date dataDiAggiunta) {
        this.dataDiAggiunta = dataDiAggiunta;
    }


    public Utente getAutore() {
        return autore;
    }

    public void setAutore(Utente autore) {
        this.autore = autore;
    }

    public Struttura getStruttura() {
        return struttura;
    }

    public void setStruttura(Struttura struttura) {
        this.struttura = struttura;
    }


    public List<LikesUtenti> getLikesUtenti() {return likesUtenti;}

    public void setLikesUtenti(List<LikesUtenti> likesUtenti) { }


    public List<DislikesUtenti> getDislikesUtenti() {
        return dislikesUtenti;
    }

    public void setDislikesUtenti(List<DislikesUtenti> dislikesUtenti) {

    }

    public static class DataComparator implements Comparator<Recensione> {


        @Override
        public int compare(Recensione o1, Recensione o2) {

            if(o1.getDataDiAggiunta().before(o2.getDataDiAggiunta())){
                return -1;
            }
            else if(o1.getDataDiAggiunta().after(o2.getDataDiAggiunta())){
                return 1;
            }
            else{ return 0;}
        }
    }

    public static class VoteComparator implements Comparator<Recensione> {


        @Override
        public int compare(Recensione o1, Recensione o2) {

            if(o1.getVoto() <o2.getVoto()){
                return -1;
            }
            else if(o1.getVoto() >o2.getVoto()){
                return 1;
            }
            else{ return 0;}
        }
    }



}
