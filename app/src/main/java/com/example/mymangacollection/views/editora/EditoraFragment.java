package com.example.mymangacollection.views.editora;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymangacollection.R;
import com.example.mymangacollection.views.colecao.CadastrarColecaoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditoraFragment extends Fragment {
    private FloatingActionButton btnAddEditora;
    private Intent cadastrarEditora;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editora, container, false);
        getActivity().setTitle("Editoras");

        this.registrarComponentes(rootView);
        this.registrarEventos();

        return rootView;
    }

    private void registrarComponentes(View view) {
        btnAddEditora = view.findViewById(R.id.btnAddEditora);
        cadastrarEditora = new Intent(getActivity(), CadastrarEditoraActivity.class);
    }

    private void registrarEventos() {
        this.btnAddEditora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(cadastrarEditora);
            }
        });
    }
}
