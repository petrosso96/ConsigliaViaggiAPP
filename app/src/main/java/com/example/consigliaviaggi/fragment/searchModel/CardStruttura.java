package com.example.consigliaviaggi.fragment.searchModel;

import android.graphics.Bitmap;

public class CardStruttura {

    private String immagine;
    private String nomeStruttura;
    private String descrizioneStruttura;
    private String id;

    public CardStruttura(String id,String immagine, String nomeStruttura, String descrizioneStruttura) {
        this.id = id;
        this.immagine = immagine;
        this.nomeStruttura = nomeStruttura;
        this.descrizioneStruttura = descrizioneStruttura;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getNomeStruttura() {
        return nomeStruttura;
    }

    public void setNomeStruttura(String nomeStruttura) {
        this.nomeStruttura = nomeStruttura;
    }

    public String getDescrizioneStruttura() {
        return descrizioneStruttura;
    }

    public void setDescrizioneStruttura(String descrizioneStruttura) {
        this.descrizioneStruttura = descrizioneStruttura;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
