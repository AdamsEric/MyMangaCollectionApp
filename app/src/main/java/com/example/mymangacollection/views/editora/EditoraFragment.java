package com.example.mymangacollection.views.editora;

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
import com.example.mymangacollection.adapters.EditoraAdapter;
import com.example.mymangacollection.helpers.ActivityRequestCodeEnum;
import com.example.mymangacollection.models.Editora;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class EditoraFragment extends Fragment {
    private RecyclerView rcvEditoraList;
    private SwipeRefreshLayout swpEditoraList;
    private LinearLayout lytEditoraEmptyList;
    private LinearLayout lytEditoraError;
    private LinearLayout lytEditoraLoading;
    private FloatingActionButton btnEditoraAdd;
    private Button btnEditoraErrorRefresh;

    private Intent formularioEditora;
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    private EditoraAdapter editoraAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_editora, container, false);
        getActivity().setTitle("Editoras");

        this.registrarComponentes(rootView);
        this.registrarEventos();
        this.carregarEditoras();

        return rootView;
    }

    private void registrarComponentes(View view) {
        rcvEditoraList = (RecyclerView) view.findViewById(R.id.rcvEditoraList);
        swpEditoraList = (SwipeRefreshLayout) view.findViewById(R.id.swpEditoraList);
        lytEditoraEmptyList = (LinearLayout) view.findViewById(R.id.lytEditoraEmptyList);
        lytEditoraLoading = (LinearLayout) view.findViewById(R.id.lytEditoraLoading);
        lytEditoraError = (LinearLayout) view.findViewById(R.id.lytEditoraError);
        btnEditoraAdd = (FloatingActionButton) view.findViewById(R.id.btnEditoraAdd);
        btnEditoraErrorRefresh = (Button) view.findViewById(R.id.btnEditoraErrorRefresh);
        formularioEditora = new Intent(getActivity(), EditoraFormActivity.class);
        requestQueue = Volley.newRequestQueue(getContext());
    }

    private void registrarEventos() {
        this.btnEditoraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(formularioEditora, ActivityRequestCodeEnum.EDITORA.getValue());
            }
        });
        this.swpEditoraList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarEditoras();
            }
        });
        this.btnEditoraErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarEditoras();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityRequestCodeEnum.EDITORA.getValue()) {
            this.carregarEditoras();
        }
    }

    private void carregarEditoras() {
        String URL = getResources().getString(R.string.url_api) + "editora";
        final Fragment fragment = this;

        if (!swpEditoraList.isRefreshing()) {
            lytEditoraEmptyList.setVisibility(View.GONE);
            lytEditoraLoading.setVisibility(View.VISIBLE);
            rcvEditoraList.setVisibility(View.GONE);
            lytEditoraError.setVisibility(View.GONE);
        }

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Editora>>() {}.getType();
                ArrayList<Editora> dados = gson.fromJson(response, objectType);
                editoraAdapter = new EditoraAdapter(dados, fragment);
                editoraAdapter.notifyDataSetChanged();
                rcvEditoraList.setAdapter(editoraAdapter);

                if (swpEditoraList.isRefreshing()) {
                    swpEditoraList.setRefreshing(false);
                }

                if (dados.size() == 0) {
                    lytEditoraEmptyList.setVisibility(View.VISIBLE);
                    lytEditoraLoading.setVisibility(View.GONE);
                    rcvEditoraList.setVisibility(View.GONE);
                    lytEditoraError.setVisibility(View.GONE);
                } else {
                    lytEditoraEmptyList.setVisibility(View.GONE);
                    lytEditoraLoading.setVisibility(View.GONE);
                    rcvEditoraList.setVisibility(View.VISIBLE);
                    lytEditoraError.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (swpEditoraList.isRefreshing()) {
                    swpEditoraList.setRefreshing(false);
                }

                lytEditoraEmptyList.setVisibility(View.GONE);
                lytEditoraLoading.setVisibility(View.GONE);
                rcvEditoraList.setVisibility(View.GONE);
                lytEditoraError.setVisibility(View.VISIBLE);
            }
        });

        this.requestQueue.add(request);
    }
}
