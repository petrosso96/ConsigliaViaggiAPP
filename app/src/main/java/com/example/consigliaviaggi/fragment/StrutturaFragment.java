package com.example.consigliaviaggi.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.consigliaviaggi.MainActivity;
import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.recensioneModel.CardRecensione;
import com.example.consigliaviaggi.model.Filtri;
import com.example.consigliaviaggi.model.MostraCome;
import com.example.consigliaviaggi.model.Rank;
import com.example.consigliaviaggi.model.Recensione;
import com.example.consigliaviaggi.model.Struttura;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class StrutturaFragment extends Fragment {

    private String idStruttura;
    private View view;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<CardRecensione> listaRecensioni;

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

        view = inflater.inflate(R.layout.fragment_struttura, container, false);

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

        listaRecensioni = new ArrayList<CardRecensione>();

        OrdinaRecensioniREST ordinaRecensioniREST = new OrdinaRecensioniREST("recenti");
        ordinaRecensioniREST.execute();

        buildListaRecensioni(listaRecensioni);


      return view;
    }

    public void buildListaRecensioni(ArrayList<CardRecensione> listaRecensioni){

        recyclerView = view.findViewById(R.id.lista_recensioni);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new RecensioniRecycleView(listaRecensioni);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);



    }



     class OrdinaRecensioniREST extends AsyncTask<Void, Void, Recensione[]> {


         String criterioDiOrdinamento;

         public OrdinaRecensioniREST(String criterio) {

             this.criterioDiOrdinamento = criterio;
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
         protected void onPostExecute(Recensione[] arrayRecensioni) {

             for (Recensione recensione:arrayRecensioni) {


                 String nomeRecensore = getNomeRecensore(recensione);
                 String rankRecensore = getRankRecensore(recensione);
                 Integer likesNumber = recensione.getLikes();
                 Integer dislikesNumber = recensione.getDislikes();


                 listaRecensioni.add(
                         new CardRecensione(recensione.getVoto(), nomeRecensore, rankRecensore,
                                            recensione.getDescrizione(), likesNumber.toString(), dislikesNumber.toString()
                         )
                 );

             }
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