package com.example.consigliaviaggi.fragment.strutturaModel;

public class CardRecensione {

    private int votoRecensione;
    private String nomeAutoreRecensione;
    private String rankAutoreRecensione;
    private String descrizioneRecensione;
    private String likeNumber;
    private String dislikeNumber;
    private long recensioneID;

    public CardRecensione(int votoRecensione, String nomeAutoreRecensione, String rankAutoreRecensione, String descrizioneRecensione, String likeNumber, String dislikeNumber,long recensioneID) {
        this.votoRecensione = votoRecensione;
        this.nomeAutoreRecensione = nomeAutoreRecensione;
        this.rankAutoreRecensione = rankAutoreRecensione;
        this.descrizioneRecensione = descrizioneRecensione;
        this.likeNumber = likeNumber;
        this.dislikeNumber = dislikeNumber;
        this.recensioneID = recensioneID;
    }

    public long getRecensioneID() {
        return recensioneID;
    }

    public void setRecensioneID(long recensioneID) {
        this.recensioneID = recensioneID;
    }

    public int getVotoRecensione() {
        return votoRecensione;
    }

    public void setVotoRecensione(int votoRecensione) {
        this.votoRecensione = votoRecensione;
    }

    public String getNomeAutoreRecensione() {
        return nomeAutoreRecensione;
    }

    public void setNomeAutoreRecensione(String nomeAutoreRecensione) {
        this.nomeAutoreRecensione = nomeAutoreRecensione;
    }

    public String getRankAutoreRecensione() {
        return rankAutoreRecensione;
    }

    public void setRankAutoreRecensione(String rankAutoreRecensione) {
        this.rankAutoreRecensione = rankAutoreRecensione;
    }

    public String getDescrizioneRecensione() {
        return descrizioneRecensione;
    }

    public void setDescrizioneRecensione(String descrizioneRecensione) {
        this.descrizioneRecensione = descrizioneRecensione;
    }

    public String getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(String likeNumber) {
        this.likeNumber = likeNumber;
    }

    public String getDislikeNumber() {
        return dislikeNumber;
    }

    public void setDislikeNumber(String dislikeNumber) {
        this.dislikeNumber = dislikeNumber;
    }
}
