package com.example.consigliaviaggi.fragment.strutturaModel;


import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.WrapperCredenzialiURL;
import com.example.consigliaviaggi.model.Recensione;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class RecensioniRecycleView extends RecyclerView.Adapter<RecensioniRecycleView.RecensioniViewHolder> {

    private ArrayList<CardRecensione> listaRecensioni;
    private WrapperCredenzialiURL wrapperCredenzialiURL;

    public RecensioniRecycleView(ArrayList<CardRecensione> listaRecensioni, WrapperCredenzialiURL wrapperCredenzialiURL){

        this.listaRecensioni =listaRecensioni;
        this.wrapperCredenzialiURL = wrapperCredenzialiURL;
    }

    @NonNull
    @Override
    public RecensioniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recensione_card,parent,false);




        return new RecensioniViewHolder(view);
    }

    class AggiungiPreferenzaRecensioneREST extends AsyncTask<Void,Void,Boolean>{

        private WrapperCredenzialiURL wrapperCredenziali;
        private String preferenza;
        private Long recensioneID;

        public AggiungiPreferenzaRecensioneREST(WrapperCredenzialiURL wrapperCredenzialiURL,String preferenza, long recensioneID){
            this.wrapperCredenziali = wrapperCredenzialiURL;
            this.preferenza = preferenza;
            this.recensioneID = recensioneID;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }



        @Override
        protected Boolean doInBackground(Void... voids) {


            final String url = wrapperCredenziali.getURL()+"/user/"+recensioneID.toString()+"/"+preferenza;

            System.out.println(url);


            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            HttpAuthentication authHeader = new HttpBasicAuthentication(wrapperCredenziali.getUsername(), wrapperCredenziali.getPassword());
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity requestEntity = new HttpEntity(requestHeaders);


            try {

                restTemplate.put(url, requestEntity );

                return true;
            }
            catch (HttpClientErrorException e){

                return false;
            }

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecensioniViewHolder holder, int position) {

        final CardRecensione recensioneCorrente = listaRecensioni.get(position);
        holder.autoreRecensione.setText(recensioneCorrente.getNomeAutoreRecensione());
        holder.votoRecensione.setMax(5);
        holder.votoRecensione.setNumStars(5);
        holder.votoRecensione.setRating(recensioneCorrente.getVotoRecensione());
        holder.likeNumber.setText(recensioneCorrente.getLikeNumber());
        holder.dislikeNumber.setText(recensioneCorrente.getDislikeNumber());
        holder.rankUtente.setText(recensioneCorrente.getRankAutoreRecensione());
        holder.descrizione.setText(recensioneCorrente.getDescrizioneRecensione());
        final long recensioneId = recensioneCorrente.getRecensioneID();

        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AggiungiPreferenzaRecensioneREST aggiungiPreferenzaRecensioneREST = new AggiungiPreferenzaRecensioneREST(wrapperCredenzialiURL,"addLike",recensioneId);
                aggiungiPreferenzaRecensioneREST.execute();



            }
        });

        holder.dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AggiungiPreferenzaRecensioneREST aggiungiPreferenzaRecensioneREST = new AggiungiPreferenzaRecensioneREST(wrapperCredenzialiURL,"addDislike",recensioneId);
                aggiungiPreferenzaRecensioneREST.execute();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaRecensioni.size();
    }

    public static class RecensioniViewHolder extends RecyclerView.ViewHolder{
        public TextView autoreRecensione;
        public RatingBar votoRecensione;
        public ImageButton likeButton;
        public TextView likeNumber;
        public ImageButton dislikeButton;
        public TextView dislikeNumber;
        public TextView descrizione;
        public TextView rankUtente;


        public RecensioniViewHolder(@NonNull View itemView) {
            super(itemView);
            rankUtente = itemView.findViewById(R.id.rank_recensore);
            autoreRecensione = itemView.findViewById(R.id.nome_autore_recensione);
            votoRecensione = itemView.findViewById(R.id.ratingBar);
            likeButton = itemView.findViewById(R.id.like_button);
            likeNumber = itemView.findViewById(R.id.like_number);
            dislikeButton = itemView.findViewById(R.id.dislike_button);
            dislikeNumber = itemView.findViewById(R.id.dislike_number);
            descrizione = itemView.findViewById(R.id.descrizione_recensione);



        }





    }
}
