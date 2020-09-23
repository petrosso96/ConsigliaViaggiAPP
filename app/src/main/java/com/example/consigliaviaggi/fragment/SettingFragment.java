package com.example.consigliaviaggi.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.consigliaviaggi.R;
import com.example.consigliaviaggi.fragment.menuImpostazioni.InformazioniPersonali;
import com.example.consigliaviaggi.fragment.menuImpostazioni.LineeGuida;
import com.example.consigliaviaggi.fragment.menuImpostazioni.Login;
import com.example.consigliaviaggi.fragment.menuImpostazioni.Registrazione;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends PreferenceFragmentCompat {



    private View view;

    public SettingFragment() {


    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {


        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences( this.getContext());

        boolean isLogged = sp.getBoolean("isLogged",false);



        if(isLogged) {

            setPreferencesFromResource(R.xml.settings_is_logged, rootKey);

            Preference voceInformazioniPersonaliListener = findPreference("informazioni_personali");
            Preference voceLogoutListener = findPreference("logout");

            voceInformazioniPersonaliListener.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {


                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new InformazioniPersonali())
                            .commit();

                    return true;
                }
            });


            voceLogoutListener.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences( getContext());
                    sp.edit().putBoolean("isLogged",false).apply();


                    AccountManager accountManager = AccountManager.get(getContext());
                    Account[] accounts = accountManager.getAccounts();

                    accountManager.removeAccountExplicitly(accounts[0]);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new HomeFragment())
                            .commit();


                    return true;
                }
            });

        }else {

            setPreferencesFromResource(R.xml.settings, rootKey);

            Preference voceLoginListener = findPreference("login");
            Preference voceRegistratiListener = findPreference("registrati");


          voceLoginListener.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new Login())
                        .commit();

                return true;
            }
           });

            voceRegistratiListener.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new Registrazione())
                            .commit();

                    return true;
                }
            });
        }

        Preference voceLineeGuidaListener = findPreference("linee_guida");

        voceLineeGuidaListener.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {


                getActivity().getSupportFragmentManager().
                        beginTransaction().
                        replace(R.id.fragment_container,new LineeGuida()).
                        commit();

                return true;
            }
        });

    }

}
