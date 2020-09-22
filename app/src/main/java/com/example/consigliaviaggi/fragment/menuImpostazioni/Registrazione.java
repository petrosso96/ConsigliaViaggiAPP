package com.example.consigliaviaggi.fragment.menuImpostazioni;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.consigliaviaggi.MainActivity;
import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.HomeFragment;
import com.example.consigliaviaggi.model.CredenzialiUtente;
import com.example.consigliaviaggi.model.Gender;
import com.example.consigliaviaggi.model.Utente;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;

import androidx.fragment.app.Fragment;


public class Registrazione extends Fragment {

    protected static final String TAG = MainActivity.class.getSimpleName();
    private View view;
    private String sesso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_registrazione, container, false);


        Button confermaRegistrazione = view.findViewById(R.id.salva_registrazione);

        confermaRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registra();

            }
        });



        return view;
    }


    private void registra(){
        RegistrazioneREST restTask = new RegistrazioneREST();
        restTask.execute();

    }

    private class RegistrazioneREST extends AsyncTask<Void,Void, String> {
        private String nome;
        private String cognome;
        private String nomeUtente;
        private Date dataDiNascita;
        private String password;
        private String city;
        private String indirizzoEmail;
        private Gender sesso;
        private String confermaPassword;


        @Override
        protected void onPreExecute() {

            // build the message object
            EditText editText = (EditText) view.findViewById(R.id.nome_utente_registrazione);
            this.nomeUtente = editText.getText().toString();

            editText = (EditText) view.findViewById(R.id.password_registrazione);
            this.password = editText.getText().toString();

            editText = (EditText) view.findViewById(R.id.nome_registrazione);
            this.nome = editText.getText().toString();

            editText = (EditText) view.findViewById(R.id.cognome_registrazione);
            this.cognome = editText.getText().toString();

            DatePicker data =  view.findViewById(R.id.dataNascita_registrazione);
            int giorno = data.getDayOfMonth();
            int mese = data.getMonth();
            int anno = data.getYear();
            dataDiNascita = new Date(giorno,mese,anno);

            editText = (EditText) view.findViewById(R.id.città_registrazione);
            this.city = editText.getText().toString();

            editText = (EditText) view.findViewById(R.id.indirizzo_email_registrazione);
            this.indirizzoEmail = editText.getText().toString();

            EditText confermaPassword = view.findViewById(R.id.conferma_password_registrazione);
            this.confermaPassword = confermaPassword.getText().toString();

            RadioGroup gruppoBottoniSesso = view.findViewById(R.id.sesso_bottoni);

            gruppoBottoniSesso.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch(checkedId)
                    {
                        case R.id.sesso_maschio_registrazione:
                            sesso=Gender.maschio;
                            break;
                        case R.id.sesso_femmina_registrazione:
                            sesso =Gender.femmina;
                            break;
                        case R.id.sesso_altro_registrazione:
                            sesso = Gender.altro;
                            break;
                    }
                }
            });
        }


        @Override
        protected String doInBackground(Void... params) {

            if(password.equals(confermaPassword)) {

                final String url = getString(R.string.base_uri) + "/all/registrazione";


                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                requestHeaders.setContentType(MediaType.APPLICATION_JSON);

                Utente utente = new Utente(nomeUtente, nome, cognome, indirizzoEmail, password, sesso, city, dataDiNascita);

                HttpEntity<Utente> requestEntity = new HttpEntity<>(utente, requestHeaders);


                try {
                    Log.d(TAG, url);

                    ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

                    return responseEntity.getBody();

                } catch (HttpClientErrorException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);

                    HttpStatus statusCode =e.getStatusCode();

                    if(statusCode.value() == 406){

                    displayResponse("Utente già presente!");
                    }
                    return null;
                }

            }else{

                displayResponse("le password non combaciano!");

                return null;

            }

        }

        @Override
        protected void onPostExecute(String result) {

            if (result != null) {

                displayResponse("Registrazione effettuata con successo!");

                AccountManager accountManager = AccountManager.get(getContext());

                final Account account = new Account(nomeUtente, "com.consigliaviaggi");
                accountManager.addAccountExplicitly(account, password, null);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

            }


        }
    }
    private void displayResponse(String response) {
        Toast.makeText(this.getContext(), response, Toast.LENGTH_LONG).show();

    }

}