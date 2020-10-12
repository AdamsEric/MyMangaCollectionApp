package com.example.mymangacollection.views.serie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymangacollection.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SerieFragment extends Fragment {
    private FloatingActionButton btnAddSerie;
    private Intent cadastrarSerie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_serie, container, false);
        getActivity().setTitle("Séries");

        this.registrarComponentes(rootView);
        this.registrarEventos();

        return rootView;
    }

    private void registrarComponentes(View view) {
        btnAddSerie = view.findViewById(R.id.btnAddSerie);
        cadastrarSerie = new Intent(getActivity(), CadastrarSerieActivity.class);
    }

    private void registrarEventos() {
        this.btnAddSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(cadastrarSerie);
            }
        });
    }
}
