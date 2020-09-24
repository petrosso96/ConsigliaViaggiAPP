package com.example.consigliaviaggi.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.consigliaviaggi.MainActivity;
import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.filtri.ListaFiltriFragment;
import com.example.consigliaviaggi.fragment.searchModel.CardStruttura;
import com.example.consigliaviaggi.model.Filtri;
import com.example.consigliaviaggi.model.Struttura;
import com.example.consigliaviaggi.model.Utente;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.LOCATION_SERVICE;

public class SearchFragment extends Fragment {

    private View view;
    private String nome = null;
    private int distanza = -1;
    private String city = null;
    private String categoria = null;
    private double latitudine = -1;
    private double longitudine = -1;
    private int prezzo = -1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Location location;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private int MIN_TIME_BW_UPDATES =6000;
    private int MIN_DISTANCE_CHANGE_FOR_UPDATES =100;

    private RecyclerView recyclerView;
    private AdapterRecycleView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<CardStruttura> elencoStrutture;

    public SearchFragment() {
    }


    private Location getLocation() {

        Location location = null;
        if(!((ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED))) {


            locationManager = (LocationManager) getContext()
                    .getSystemService(LOCATION_SERVICE);

            locationListener = new MyLocationListener();

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                boolean canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitudine = location.getLatitude();
                            longitudine = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitudine = location.getLatitude();
                                longitudine = location.getLongitude();
                            }
                        }
                    }
                }
            }

        }
        return location;

    }




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);
        Bundle bundle = getArguments();

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

         location = getLocation();

        if (bundle != null) {

            city = bundle.getString("city");
            categoria = bundle.getString("categoria");
            prezzo = bundle.getInt("prezzo");
            distanza = bundle.getInt("distanza");

        }

        ImageButton searchButton = view.findViewById(R.id.search_button);
        Button filtriButton = view.findViewById(R.id.button_filtri);
        final EditText searchField = view.findViewById(R.id.search_field);

        filtriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListaFiltriFragment()).commit();

            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!searchField.getText().toString().isEmpty()) {

                    nome = searchField.getText().toString();
                }

                if (distanza != -1 && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    Toast toast = Toast.makeText(getContext(), "Devi attivare il GPS se vuoi cercare le strutture entro una certa distanza", Toast.LENGTH_SHORT);
                    toast.show();

                } else {



                    if(location != null) {
                        System.out.println("ho passato il bottino");
                        longitudine = location.getLongitude();
                        latitudine = location.getLatitude();
                    }

                    Filtri filtri = new Filtri(nome, city, categoria, latitudine, longitudine, distanza, prezzo);

                    RicercaStruttureREST ricercaStruttureREST = new RicercaStruttureREST(filtri);
                    ricercaStruttureREST.execute();
                }


            }
        });


        return view;
    }


    private class RicercaStruttureREST extends AsyncTask<Void, Void, Struttura[]> {

        private Filtri filtri;

        public RicercaStruttureREST(Filtri filtri) {

            this.filtri = filtri;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Struttura[] doInBackground(Void... voids) {

            final String url = getString(R.string.base_uri) + "/all/ricerca";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);

            System.out.println(filtri);
            HttpEntity requestEntity = new HttpEntity(filtri, requestHeaders);

            try {
                Log.d(TAG, url);

                ResponseEntity<Struttura[]> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Struttura[].class);


                Struttura[] strutture = responseEntity.getBody();
                return strutture;

            } catch (HttpClientErrorException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
                return null;
            }


        }

        @Override
        protected void onPostExecute(Struttura[] arrayStrutture) {

            if (arrayStrutture == null) {
                Toast.makeText(getContext(), "Nessuna struttura trovata", Toast.LENGTH_SHORT).show();
            } else {

                elencoStrutture = new ArrayList<>();
                CardStruttura cardCorrente;

                for (Struttura struttura: arrayStrutture ) {


                    cardCorrente = new CardStruttura(struttura.getId(),struttura.getFoto(),struttura.getNome(),struttura.getDescrizione());
                    elencoStrutture.add(cardCorrente);

                }

                buildListaStrutture(elencoStrutture);

            }

        }
    }

    private void buildListaStrutture(final ArrayList<CardStruttura> elencoStrutture){

        recyclerView = view.findViewById(R.id.recycler_view_lista_strutture);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new AdapterRecycleView(elencoStrutture);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new AdapterRecycleView.OnItemClickListener() {
            @Override
            public void onIntemClick(int position) {

                CardStruttura strutturaSelezionata = elencoStrutture.get(position);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new StrutturaFragment(strutturaSelezionata.getId())).commit();
            }
        });
    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {

            longitudine = loc.getLongitude();
            latitudine = loc.getLatitude();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }





}
