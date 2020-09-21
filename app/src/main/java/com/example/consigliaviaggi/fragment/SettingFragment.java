package com.example.consigliaviaggi.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.menuImpostazioni.LineeGuida;
import com.example.consigliaviaggi.fragment.menuImpostazioni.Login;
import com.example.consigliaviaggi.fragment.menuImpostazioni.Registrazione;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {


    TextView login;
    TextView registrazione;
    TextView lineeGuida;

    public SettingFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_setting,container,false);

        login = view.findViewById(R.id.login_button_setting);
        registrazione =  view.findViewById(R.id.registrazione_button_setting);
        lineeGuida = view.findViewById(R.id.linee_guida_button_setting);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new Login())
                        .commit();

            }
        });

        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new Registrazione())
                        .commit();

            }
        });

        lineeGuida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new LineeGuida())
                        .commit();

            }
        });

        return view;


    }





}
