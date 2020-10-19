package com.example.mymangacollection.views.colecao;

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
import com.example.mymangacollection.R;
import com.example.mymangacollection.adapters.ColecaoAdapter;
import com.example.mymangacollection.helpers.ActivityRequestCodeEnum;
import com.example.mymangacollection.models.Colecao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ColecaoFragment extends Fragment {
    private RecyclerView rcvColecaoList;
    private SwipeRefreshLayout swpColecaoList;
    private LinearLayout lytColecaoEmptyList;
    private LinearLayout lytColecaoError;
    private LinearLayout lytColecaoLoading;
    private FloatingActionButton btnColecaoAdd;
    private Button btnColecaoErrorRefresh;

    private Intent formularioColecao;
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    private ColecaoAdapter colecaoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colecao, container, false);
        getActivity().setTitle("Coleções");

        this.registrarComponentes(rootView);
        this.registrarEventos();
        this.carregarColecoes();

        return rootView;
    }

    private void registrarComponentes(View view) {
        rcvColecaoList = (RecyclerView) view.findViewById(R.id.rcvColecaoList);
        swpColecaoList = (SwipeRefreshLayout) view.findViewById(R.id.swpColecaoList);
        lytColecaoEmptyList = (LinearLayout) view.findViewById(R.id.lytColecaoEmptyList);
        lytColecaoLoading = (LinearLayout) view.findViewById(R.id.lytColecaoLoading);
        lytColecaoError = (LinearLayout) view.findViewById(R.id.lytColecaoError);
        btnColecaoAdd = (FloatingActionButton) view.findViewById(R.id.btnColecaoAdd);
        btnColecaoErrorRefresh = (Button) view.findViewById(R.id.btnColecaoErrorRefresh);
        formularioColecao = new Intent(getActivity(), ColecaoAddActivity.class);
        requestQueue = Volley.newRequestQueue(getContext());
    }

    private void registrarEventos() {
        this.btnColecaoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(formularioColecao, ActivityRequestCodeEnum.COLECAO.getValue());
            }
        });
        this.swpColecaoList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarColecoes();
            }
        });
        this.btnColecaoErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarColecoes();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityRequestCodeEnum.COLECAO.getValue()) {
            this.carregarColecoes();
        }
    }

    private void carregarColecoes() {
        String URL = getResources().getString(R.string.url_api) + "colecao";
        final Fragment fragment = this;

        if (!swpColecaoList.isRefreshing()) {
            lytColecaoEmptyList.setVisibility(View.GONE);
            lytColecaoLoading.setVisibility(View.VISIBLE);
            rcvColecaoList.setVisibility(View.GONE);
            lytColecaoError.setVisibility(View.GONE);
        }

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Colecao>>() {
                }.getType();
                ArrayList<Colecao> dados = gson.fromJson(response, objectType);
                colecaoAdapter = new ColecaoAdapter(dados, fragment);
                colecaoAdapter.notifyDataSetChanged();
                rcvColecaoList.setAdapter(colecaoAdapter);

                if (swpColecaoList.isRefreshing()) {
                    swpColecaoList.setRefreshing(false);
                }

                if (dados.size() == 0) {
                    lytColecaoEmptyList.setVisibility(View.VISIBLE);
                    lytColecaoLoading.setVisibility(View.GONE);
                    rcvColecaoList.setVisibility(View.GONE);
                    lytColecaoError.setVisibility(View.GONE);
                } else {
                    lytColecaoEmptyList.setVisibility(View.GONE);
                    lytColecaoLoading.setVisibility(View.GONE);
                    rcvColecaoList.setVisibility(View.VISIBLE);
                    lytColecaoError.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (swpColecaoList.isRefreshing()) {
                    swpColecaoList.setRefreshing(false);
                }

                lytColecaoEmptyList.setVisibility(View.GONE);
                lytColecaoLoading.setVisibility(View.GONE);
                rcvColecaoList.setVisibility(View.GONE);
                lytColecaoError.setVisibility(View.VISIBLE);
            }
        });

        this.requestQueue.add(request);
    }
}
