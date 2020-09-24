package com.example.consigliaviaggi.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.recensioneModel.CardRecensione;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecensioniRecycleView extends RecyclerView.Adapter<RecensioniRecycleView.RecensioniViewHolder> {

    private ArrayList<CardRecensione> listaRecensioni;

    public RecensioniRecycleView(ArrayList<CardRecensione> listaRecensioni){

        this.listaRecensioni =listaRecensioni;
    }

    @NonNull
    @Override
    public RecensioniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recensione_card,parent,false);
        return new RecensioniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecensioniViewHolder holder, int position) {

        CardRecensione recensioneCorrente = listaRecensioni.get(position);
        holder.autoreRecensione.setText(recensioneCorrente.getNomeAutoreRecensione());
        holder.votoRecensione.setMax(5);
        holder.votoRecensione.setNumStars(5);
        holder.votoRecensione.setRating(recensioneCorrente.getVotoRecensione());
        holder.likeNumber.setText(recensioneCorrente.getLikeNumber());
        holder.dislikeNumber.setText(recensioneCorrente.getDislikeNumber());
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

        public RecensioniViewHolder(@NonNull View itemView) {
            super(itemView);
            autoreRecensione = itemView.findViewById(R.id.nome_autore_recensione);
            votoRecensione = itemView.findViewById(R.id.ratingBar);
            likeButton = itemView.findViewById(R.id.like_button);
            likeNumber = itemView.findViewById(R.id.like_number);
            dislikeButton = itemView.findViewById(R.id.dislike_button);
            dislikeNumber = itemView.findViewById(R.id.dislike_number);
        }
    }
}
