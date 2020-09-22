package com.example.consigliaviaggi.fragment;

import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class SettingFragment extends Fragment {


    TextView login;
    TextView registrazione;
    TextView lineeGuida;
    View view;

    public SettingFragment() {


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        SharedPreferences sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);

        if(sp.getBoolean("isLogged", false)){

             view = inflater.inflate(R.layout.fragment_setting, container, false);


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

        }else{

            view = inflater.inflate(R.layout.fragment_setting_logged, container, false);


            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_settings_is_logged);


            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
            recyclerView.setLayoutManager(layoutManager);

            // specify an adapter (see also next example)




        }






        return view;


    }


    private static class AdapterListSettingIsLogged extends  RecyclerView.Adapter<AdapterListSettingIsLogged.MyViewHolder> {

        private String[] mDataset;

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
         static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView;
            public MyViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }

        public AdapterListSettingIsLogged(String[] myDataset) {
            mDataset = myDataset;
        }



    }




}
