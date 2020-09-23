package com.example.consigliaviaggi.fragment.menuImpostazioni;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consigliaviaggi.MainActivity;
import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.model.CredenzialiUtente;
import com.example.consigliaviaggi.model.Utente;

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
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import androidx.fragment.app.Fragment;

public class InformazioniPersonali extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private View view;
    private String nomeUtente;
    private String password;
    private TextView nome;
    private TextView cognome;
    private TextView sesso;
    private TextView indirizzoEmail;
    private TextView dataDinascita;
    private TextView mostraCome;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_informazioni_personali, container, false);


        InformazioniPersonali.InformazioniPersonaliREST restTask = new InformazioniPersonali.InformazioniPersonaliREST();
        restTask.execute();




        return view;
    }




    class InformazioniPersonaliREST extends AsyncTask<Void,Void,Utente>{


        @Override
        protected void onPreExecute() {

            AccountManager accountManager = AccountManager.get(getContext());
            Account[] accounts = accountManager.getAccounts();
            nomeUtente = accounts[0].name;
            password = accountManager.getPassword(accounts[0]);


             nome = view.findViewById(R.id.utente_nome);
             cognome = view.findViewById(R.id.utente_cognome);
             sesso = view.findViewById(R.id.utente_sesso);
             indirizzoEmail = view.findViewById(R.id.utente_indirizzo_email);
             dataDinascita = view.findViewById(R.id.utente_data_nascita);
             mostraCome = view.findViewById(R.id.utente_mostra_come);


        }

        @Override
        protected Utente doInBackground(Void... voids) {

            final String url = getString(R.string.base_uri) + "/user/"+nomeUtente;

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            HttpAuthentication authHeader = new HttpBasicAuthentication(nomeUtente, password);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity requestEntity = new HttpEntity("",requestHeaders);

            try {
                Log.d(TAG, url);

                ResponseEntity<Utente> responseEntity = restTemplate.exchange(url, HttpMethod.GET,requestEntity,Utente.class);
                System.out.println(responseEntity.getBody());

                return responseEntity.getBody();


            }catch (HttpClientErrorException e){
                Log.e(TAG, e.getLocalizedMessage(), e);
                return null;

            }


        }



        @Override
        protected void onPostExecute(Utente utente) {

            nome.setText(utente.getNome());
            cognome.setText(utente.getCognome());
            indirizzoEmail.setText(utente.getIndirizzoEmail());
            sesso.setText(utente.getSesso().toString());
            String data = utente.getDataDiNascita().getDay()+"/"+utente.getDataDiNascita().getMonth()+"/"+utente.getDataDiNascita().getYear();
            dataDinascita.setText(data);
            mostraCome.setText(utente.getMostraCome().toString());




        }
    }

}