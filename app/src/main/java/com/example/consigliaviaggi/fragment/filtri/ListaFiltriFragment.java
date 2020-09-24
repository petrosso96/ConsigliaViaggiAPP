package com.example.consigliaviaggi.fragment.filtri;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.SearchFragment;
import com.example.consigliaviaggi.model.TipoStruttura;

import java.util.regex.Pattern;

public class ListaFiltriFragment extends PreferenceFragmentCompat {
    private String city = null;
    private int distanza = -1;
    private String categoria = null;
    private int rangeDiPrezzo = -1;


    public boolean containsDigits(String sample){

        char[] chars = sample.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                return true;
            }
        }
        return false;

    }

    public boolean containsLetters(String sample){

        char[] chars = sample.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isLetter(c)){
                return true;
            }
        }
        return false;

    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.lista_filtri, rootKey);

        EditTextPreference fieldCity = findPreference("città_field");

        EditTextPreference fieldDistanza = findPreference("distanza_field");

        ListPreference listRangeDiPrezzo = findPreference("prezzo_value");

        final ListPreference listActivity = findPreference("categoria_attività_value");
        final ListPreference listLuoghi = findPreference("categoria_luoghi_value");
        Preference confermaFiltri = findPreference("conferma");


        assert fieldCity != null;
        fieldCity.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String cityPreValidazione = (String) newValue;

                if(!cityPreValidazione.isEmpty()) {

                    if(containsDigits(cityPreValidazione)){

                        return false;
                    }
                    else {
                        city = (String) newValue;
                        return true;
                    }
                }else{
                    city = null;
                }
                return true;

            }
        });


        assert fieldDistanza != null;
        fieldDistanza.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String distanzaPreValidazione = (String) newValue;

                if(distanzaPreValidazione.isEmpty()) {

                    distanza = -1;
                    return true;

                }else {

                    if (!containsLetters(distanzaPreValidazione)) {
                        distanza = Integer.parseInt(distanzaPreValidazione);
                        return true;
                    } else {
                        return false;
                    }
                }

            }
        });

        assert listRangeDiPrezzo != null;
        listRangeDiPrezzo.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                rangeDiPrezzo = Integer.parseInt((String)newValue);
                return true;
            }
        });


        assert listActivity != null;
        listActivity.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                categoria = (String) newValue;
                listLuoghi.setValue(null);
                return true;
            }
        });

        assert listLuoghi != null;
        listLuoghi.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                categoria = (String) newValue;
                listActivity.setValue(null);
                return true;
            }
        });

        assert confermaFiltri != null;
        confermaFiltri.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                final Bundle bundle = new Bundle();
                bundle.putString("city",city);
                bundle.putInt("distanza",distanza);
                bundle.putInt("prezzo",rangeDiPrezzo);
                bundle.putString("categoria",categoria);
                SearchFragment searchFragment = new SearchFragment();
                searchFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchFragment).commit();

                return true;

            }
        });



    }

}