package com.example.mymangacollection.views.colecao;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymangacollection.R;
import com.example.mymangacollection.models.Colecao;

public class ColecaoVolumesFragment extends Fragment {
    private Colecao colecao;

    public ColecaoVolumesFragment(Colecao colecao) {
        this.colecao = colecao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colecao_volumes, container, false);

        return rootView;
    }
}