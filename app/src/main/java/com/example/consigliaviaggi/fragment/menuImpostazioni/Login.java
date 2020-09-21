package com.example.consigliaviaggi.fragment.menuImpostazioni;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consigliaviaggi.MainActivity;
import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.HomeFragment;
import com.example.consigliaviaggi.model.CredenzialiUtente;


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


public class Login extends Fragment {

    protected static final String TAG = MainActivity.class.getSimpleName();
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);


        final Button accedi = view.findViewById(R.id.button_login);
        final TextView registrati = view.findViewById(R.id.registrati_link);

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autentica();

            }
        });

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new Registrazione())
                        .commit();
            }
        });



        return view;
    }

    private void displayResponse(String response) {
        Toast.makeText(this.getContext(), response, Toast.LENGTH_LONG).show();

    }




     private class RESTTask extends AsyncTask<Void,Void, CredenzialiUtente>{
        private  String nomeUtente;
        private  String password;

         @Override
         protected void onPreExecute() {

             // build the message object
             EditText editText = (EditText) view.findViewById(R.id.username_login);
             this.nomeUtente = editText.getText().toString();

             editText = (EditText) view.findViewById(R.id.password_login);
             this.password = editText.getText().toString();
         }


         @Override
         protected CredenzialiUtente doInBackground(Void... params) {

            final String url = getString(R.string.base_uri) + "/user/autenticazioneUtente";


            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


            HttpAuthentication authHeader = new HttpBasicAuthentication(nomeUtente, password);
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAuthorization(authHeader);
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);



             CredenzialiUtente utente = new CredenzialiUtente(nomeUtente,password);


            try {
                Log.d(TAG, url);

                ResponseEntity<CredenzialiUtente> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(utente,requestHeaders), CredenzialiUtente.class);



                return response.getBody();

            } catch (HttpClientErrorException e) {
                 Log.e(TAG, e.getLocalizedMessage(), e);
                 return null;
            }

        }

         @Override
         protected void onPostExecute(CredenzialiUtente result) {

             if( result == null) {

                 displayResponse("Errore: username o password errati!");

             }
             else {

                 AccountManager accountManager = AccountManager.get(getContext());

                 final Account account = new Account(nomeUtente, "com.consigliaviaggi");
                 accountManager.addAccountExplicitly(account, password, null);

                 getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

             }


         }
    }

    public void autentica(){
       RESTTask restTask = new RESTTask();
       restTask.execute();

    }


}