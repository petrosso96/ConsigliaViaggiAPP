package com.example.consigliaviaggi.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.consigliaviaggi.R;


public class StrutturaFragment extends Fragment {

    private String idStruttura;

    public StrutturaFragment() {
        // Required empty public constructor
    }

    public StrutturaFragment(String idStruttura){
        this.idStruttura=idStruttura;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_struttura, container, false);
    }
}