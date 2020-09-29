package com.example.mymangacollection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ColecaoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colecao, container, false);
        getActivity().setTitle("Coleções");

        ListView listaColecao = (ListView) rootView.findViewById(R.id.lst_colecao);
        ArrayList<String> colecoes = obterColecoes();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, colecoes);
        listaColecao.setAdapter(arrayAdapter);

        return rootView;
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
