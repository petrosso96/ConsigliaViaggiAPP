package com.example.consigliaviaggi.fragment.Struttura;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.StrutturaFragment;
import com.example.consigliaviaggi.model.Recensione;
import com.example.consigliaviaggi.model.Struttura;
import com.google.android.gms.common.data.ObjectExclusionFilterable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

import java.util.Collections;


public class AggiungiRecensioneFragment extends Fragment {

    private View view;
    private String idStruttura;
    private EditText testoRecensione;
    private RatingBar ratingBar;
    private Recensione recensione;
    private StrutturaFragment paginaStruttura;

    public AggiungiRecensioneFragment() {
        // Required empty public constructor
    }

    public AggiungiRecensioneFragment(String idStruttura,StrutturaFragment paginaStruttura) {
        this.idStruttura = idStruttura;
        this.paginaStruttura = paginaStruttura;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_aggiungi_recensione, container, false);
        Button bottoneAggiungiRecensione = view.findViewById(R.id.button_aggiungi_recensione);
        testoRecensione = view.findViewById(R.id.recensione_aggiungi_recensione);
         ratingBar = view.findViewById(R.id.rating_aggiungi_recensione);


        bottoneAggiungiRecensione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AggiungiRecensioneREST aggiungiRecensioneREST = new AggiungiRecensioneREST();
                aggiungiRecensioneREST.execute();
            }
        });



        return view;
    }


    class AggiungiRecensioneREST extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            recensione= new Recensione((int)ratingBar.getRating(),testoRecensione.getText().toString());
        }

        @Override
        protected void onPostExecute(Boolean isRecensioneInserita) {
            super.onPostExecute(isRecensioneInserita);


            if(isRecensioneInserita){

                Toast.makeText(getContext(),"Recensione inserita con successo",Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(getContext(),"Recensione non inserita",Toast.LENGTH_SHORT).show();

            }

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,paginaStruttura)
                    .commit();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            final String url = getString(R.string.base_uri) + "/user/" + idStruttura + "/aggiungirecensione";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            AccountManager accountManager = AccountManager.get(getContext());
            Account[] accounts = accountManager.getAccounts();
            String nomeUtente = accounts[0].name;
            String password = accountManager.getPassword(accounts[0]);


            HttpAuthentication authHeader = new HttpBasicAuthentication(nomeUtente, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity requestEntity = new HttpEntity(recensione,requestHeaders);


            try {

                ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

                return true;
            }catch (HttpClientErrorException e){

                return false;
            }



        }
    }

}