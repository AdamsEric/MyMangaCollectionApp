package com.example.mymangacollection.views.serie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymangacollection.helpers.ActivityRequestCodeEnum;
import com.example.mymangacollection.R;
import com.example.mymangacollection.adapters.SerieAdapter;
import com.example.mymangacollection.models.Serie;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SerieFragment extends Fragment {
    private RecyclerView rcvSerieList;
    private FloatingActionButton btnAddSerie;
    SwipeRefreshLayout swpSerieList;

    private Intent cadastrarSerie;
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    private SerieAdapter serieAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_serie, container, false);
        getActivity().setTitle("Séries");

        this.registrarComponentes(rootView);
        this.registrarEventos();

        this.carregarSeries();

        return rootView;
    }

    private void registrarComponentes(View view) {
        rcvSerieList = (RecyclerView) view.findViewById(R.id.rcvSerieList);
        swpSerieList = (SwipeRefreshLayout) view.findViewById(R.id.swpSerieList);
        btnAddSerie = (FloatingActionButton) view.findViewById(R.id.btnAddSerie);
        cadastrarSerie = new Intent(getActivity(), CadastrarSerieActivity.class);
        requestQueue = Volley.newRequestQueue(getContext());
    }

    private void registrarEventos() {
        this.btnAddSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(cadastrarSerie, ActivityRequestCodeEnum.SERIE.getValue());
            }
        });
        this.swpSerieList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarSeries();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityRequestCodeEnum.SERIE.getValue()) {
            this.carregarSeries();
        }
    }

    private void carregarSeries() {
        String URL = getResources().getString(R.string.url_api) + "serie";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Serie>>() {}.getType();
                ArrayList<Serie> dados = gson.fromJson(response, objectType);
                serieAdapter = new SerieAdapter(dados);
                serieAdapter.notifyDataSetChanged();
                rcvSerieList.setAdapter(serieAdapter);

                if (swpSerieList.isRefreshing()) {
                    swpSerieList.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }
}
