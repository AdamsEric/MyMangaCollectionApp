package com.example.mymangacollection.views.colecao;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymangacollection.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ColecaoFragment extends Fragment {
    FloatingActionButton btnAddColecao;
    Intent cadastrarDentista;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colecao, container, false);
        getActivity().setTitle("Coleções");

        this.registrarComponentes(rootView);
        this.registrarEventos();

        return rootView;
    }

    private void registrarComponentes(View view) {
        btnAddColecao = view.findViewById(R.id.btnAddColecao);
        cadastrarDentista = new Intent(getActivity(), CadastrarColecaoActivity.class);
    }

    private void registrarEventos() {
        this.btnAddColecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(cadastrarDentista);
            }
        });
    }


    private ArrayList<String> obterColecoes() {
        ArrayList<String> colecoes = new ArrayList<String>();
        colecoes.add("Another");
        colecoes.add("JoJo's Bizarre Adventure");
        colecoes.add("One Punch Man");
        colecoes.add("Nisekoi");
        colecoes.add("Bakemonogatari");

        return colecoes;
    }
}
