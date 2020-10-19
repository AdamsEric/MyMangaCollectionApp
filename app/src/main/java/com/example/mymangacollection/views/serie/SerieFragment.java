package com.example.mymangacollection.views.serie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    private SwipeRefreshLayout swpSerieList;
    private LinearLayout lytSerieEmptyList;
    private LinearLayout lytSerieError;
    private LinearLayout lytSerieLoading;
    private FloatingActionButton btnSerieAdd;
    private Button btnSerieErrorRefresh;

    private Intent formularioSerie;
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
        lytSerieEmptyList = (LinearLayout) view.findViewById(R.id.lytSerieEmptyList);
        lytSerieLoading = (LinearLayout) view.findViewById(R.id.lytSerieLoading);
        lytSerieError = (LinearLayout) view.findViewById(R.id.lytSerieError);
        btnSerieAdd = (FloatingActionButton) view.findViewById(R.id.btnSerieAdd);
        btnSerieErrorRefresh = (Button) view.findViewById(R.id.btnSerieErrorRefresh);
        formularioSerie = new Intent(getActivity(), SerieFormActivity.class);
        requestQueue = Volley.newRequestQueue(getContext());
    }

    private void registrarEventos() {
        this.btnSerieAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(formularioSerie, ActivityRequestCodeEnum.SERIE.getValue());
            }
        });
        this.swpSerieList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarSeries();
            }
        });
        this.btnSerieErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        final Fragment fragment = this;

        if (!swpSerieList.isRefreshing()) {
            lytSerieEmptyList.setVisibility(View.GONE);
            lytSerieLoading.setVisibility(View.VISIBLE);
            rcvSerieList.setVisibility(View.GONE);
            lytSerieError.setVisibility(View.GONE);
        }

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Serie>>() {}.getType();
                ArrayList<Serie> dados = gson.fromJson(response, objectType);
                serieAdapter = new SerieAdapter(dados, fragment);
                serieAdapter.notifyDataSetChanged();
                rcvSerieList.setAdapter(serieAdapter);

                if (swpSerieList.isRefreshing()) {
                    swpSerieList.setRefreshing(false);
                }

                if (dados.size() == 0) {
                    lytSerieEmptyList.setVisibility(View.VISIBLE);
                    lytSerieLoading.setVisibility(View.GONE);
                    rcvSerieList.setVisibility(View.GONE);
                    lytSerieError.setVisibility(View.GONE);
                } else {
                    lytSerieEmptyList.setVisibility(View.GONE);
                    lytSerieLoading.setVisibility(View.GONE);
                    rcvSerieList.setVisibility(View.VISIBLE);
                    lytSerieError.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            if (swpSerieList.isRefreshing()) {
                swpSerieList.setRefreshing(false);
            }

            lytSerieEmptyList.setVisibility(View.GONE);
            lytSerieLoading.setVisibility(View.GONE);
            rcvSerieList.setVisibility(View.GONE);
            lytSerieError.setVisibility(View.VISIBLE);
            }
        });

        this.requestQueue.add(request);
    }
}
