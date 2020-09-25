package com.example.consigliaviaggi.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.consigliaviaggi.MainActivity;
import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.WrapperCredenzialiURL;
import com.example.consigliaviaggi.fragment.Struttura.AggiungiRecensioneFragment;
import com.example.consigliaviaggi.fragment.strutturaModel.CardRecensione;
import com.example.consigliaviaggi.fragment.strutturaModel.RecensioniRecycleView;
import com.example.consigliaviaggi.model.MostraCome;
import com.example.consigliaviaggi.model.Rank;
import com.example.consigliaviaggi.model.Recensione;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

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


public class StrutturaFragment extends Fragment {

    private String idStruttura;
    private String nome;
    private String descrizione;
    private String immagine;
    private View view;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CardRecensione> listaRecensioni;
    private ProgressBar progressBar;
    private StrutturaFragment riferimentoPaginaStruttura = this;

    public StrutturaFragment() {
        // Required empty public constructor
    }

    public StrutturaFragment(String idStruttura, String nomeStruttura, String descrizioneStruttura, String immagineStruttura){
        nome = nomeStruttura;
        descrizione = descrizioneStruttura;
        immagine = immagineStruttura;
        this.idStruttura=idStruttura;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_struttura, container, false);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences( this.getContext());
        boolean isLogged = sp.getBoolean("isLogged",false);

        FloatingActionButton bottoneAggiungiRecensione = view.findViewById(R.id.bottone_aggiungi_recensione);

        if(!isLogged) {

            bottoneAggiungiRecensione.setEnabled(false);

        }
        else{

            bottoneAggiungiRecensione.setEnabled(true);
            bottoneAggiungiRecensione.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new AggiungiRecensioneFragment(idStruttura,riferimentoPaginaStruttura))
                            .commit();


                }
            });
        }

        progressBar = view.findViewById(R.id.progressBar);

        ImageView imageViewImmagineStruttura = view.findViewById(R.id.immagine_struttura);
        Picasso.get().load(immagine).into(imageViewImmagineStruttura);


        TextView textViewNomeStruttura = view.findViewById(R.id.nome_struttura);
        textViewNomeStruttura.setText(nome);

        TextView textViewDescrizioneStruttura = view.findViewById(R.id.descrizione_struttura);
        textViewDescrizioneStruttura.setText(descrizione);

        Button bottoneOrdinaPer = view.findViewById(R.id.button_ordina_per);
        bottoneOrdinaPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),v);
                ListenerMenuOrdinaPer listenerMenuOrdinaPer = new ListenerMenuOrdinaPer();
                popupMenu.setOnMenuItemClickListener(listenerMenuOrdinaPer);
                popupMenu.inflate(R.menu.ordina_per_menu);
                popupMenu.show();

            }
        });



        OrdinaRecensioniREST ordinaRecensioniREST = new OrdinaRecensioniREST("recenti");
        ordinaRecensioniREST.execute();




      return view;
    }

    public void buildListaRecensioni(ArrayList<CardRecensione> listaRecensioni){

        recyclerView = view.findViewById(R.id.lista_recensioni);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecensioniRecycleView(listaRecensioni,new WrapperCredenzialiURL(getString(R.string.base_uri),getNomeUtente(),getPasswordUtente()));

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


    }


    private String getNomeUtente(){
        AccountManager accountManager = AccountManager.get(getContext());
        Account[] accounts = accountManager.getAccounts();
        return accounts[0].name;


    }

    private String getPasswordUtente(){
        AccountManager accountManager = AccountManager.get(getContext());
        Account[] accounts = accountManager.getAccounts();
        return accountManager.getPassword(accounts[0]);

    }



     class OrdinaRecensioniREST extends AsyncTask<Void, Integer, Recensione[]> {


         private String criterioDiOrdinamento;


         public OrdinaRecensioniREST(String criterio) {

             this.criterioDiOrdinamento = criterio;

         }

         @Override
         protected void onPreExecute() {
             progressBar.setProgress(0);
         }

         @Override
         protected Recensione[] doInBackground(Void... voids) {

             final String url = getString(R.string.base_uri) + "/all/" + idStruttura + "/" + criterioDiOrdinamento;
             RestTemplate restTemplate = new RestTemplate();
             restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

             HttpHeaders requestHeaders = new HttpHeaders();
             requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
             requestHeaders.setContentType(MediaType.APPLICATION_JSON);

             HttpEntity requestEntity = new HttpEntity(requestHeaders);

             try {
                 Log.d(TAG, url);

                 ResponseEntity<Recensione[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Recensione[].class);

                 //Recensione[] elencoRecensioni = responseEntity.getBody();

                 return responseEntity.getBody();

             } catch (HttpClientErrorException e) {
                 Log.e(TAG, e.getLocalizedMessage(), e);
                 return null;
             }


         }

         @Override
         protected void onProgressUpdate(Integer... values) {
             Log.d("Progress", String.valueOf(values[0]));
             progressBar.setProgress(values[0]);
         }


         @Override
         protected void onPostExecute(Recensione[] arrayRecensioni) {

             int count = 0;

             listaRecensioni = new ArrayList<>();

             for (Recensione recensione:arrayRecensioni) {

                 count++;

                 publishProgress(count);
                 String nomeRecensore = getNomeRecensore(recensione);
                 String rankRecensore = getRankRecensore(recensione);
                 Integer likesNumber = recensione.getLikes();
                 Integer dislikesNumber = recensione.getDislikes();



                 listaRecensioni.add(
                         new CardRecensione(recensione.getVoto(), nomeRecensore, rankRecensore,
                                            recensione.getDescrizione(), likesNumber.toString(), dislikesNumber.toString(),recensione.getId()
                         )
                 );

             }

             progressBar.setVisibility(View.GONE);

             buildListaRecensioni(listaRecensioni);
         }

         private String getNomeRecensore(Recensione recensione){

             MostraCome nomeConCuiIlRecensoreVuoleEssereVisto =recensione.getAutore().getMostraCome();
             String nomeRecensore;

             if(nomeConCuiIlRecensoreVuoleEssereVisto == MostraCome.NOMECOMPLETO){

                 nomeRecensore = recensione.getAutore().getNome()+" "+recensione.getAutore().getCognome();

             }
             else{

                 nomeRecensore = recensione.getAutore().getNomeUtente();
             }
             return nomeRecensore;

         }


         private String getRankRecensore(Recensione recensione){

             Rank rank;

             if(recensione.getAutore().getRank() == null){

                 rank = Rank.buono;
             }
             else{
                 rank = recensione.getAutore().getRank();
             }

             if(rank == Rank.buono){return "Buono";}
             if(rank == Rank.ottimo){return "Ottimo";}
             if(rank == Rank.eccellente){return "Eccellente";}
             else{return "Pessimo";}


         }
     }


    private class ListenerMenuOrdinaPer implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {


            OrdinaRecensioniREST ordinaRecensioniREST;

            switch (item.getItemId()){

                case R.id.meno_recenti:
                    ordinaRecensioniREST = new OrdinaRecensioniREST("menorecenti");
                    break;
                case R.id.più_recenti:
                    ordinaRecensioniREST = new OrdinaRecensioniREST("recenti");
                    break;
                case R.id.più_apprezzate:
                    ordinaRecensioniREST = new OrdinaRecensioniREST("positive");
                    break;
                case R.id.meno_apprezzate:
                    ordinaRecensioniREST = new OrdinaRecensioniREST("negative");
                    break;
                default:
                    ordinaRecensioniREST = new OrdinaRecensioniREST("recenti");
                    break;
            }


            ordinaRecensioniREST.execute();




            return true;
        }
    }
}