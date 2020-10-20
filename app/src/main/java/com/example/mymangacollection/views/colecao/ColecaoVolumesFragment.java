package com.example.mymangacollection.views.colecao;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymangacollection.R;
import com.example.mymangacollection.adapters.VolumeAdapter;
import com.example.mymangacollection.helpers.ActivityRequestCodeEnum;
import com.example.mymangacollection.models.Colecao;
import com.example.mymangacollection.models.Volume;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ColecaoVolumesFragment extends Fragment {
    private Colecao colecao;

    private RecyclerView rcvVolumeList;
    private SwipeRefreshLayout swpVolumeList;
    private LinearLayout lytVolumeEmptyList;
    private LinearLayout lytVolumeError;
    private LinearLayout lytVolumeLoading;
    private FloatingActionButton btnVolumeAdd;
    private Button btnVolumeErrorRefresh;

    private Intent formularioVolume;
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    private VolumeAdapter volumeAdapter;

    public ColecaoVolumesFragment(Colecao colecao) {
        this.colecao = colecao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colecao_volumes, container, false);

        this.registrarComponentes(rootView);
        this.registrarEventos();
        this.carregarVolumes();

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityRequestCodeEnum.VOLUME.getValue()) {
            this.carregarVolumes();
        }
    }

    private void registrarComponentes(View view) {
        rcvVolumeList = (RecyclerView) view.findViewById(R.id.rcvVolumeList);
        swpVolumeList = (SwipeRefreshLayout) view.findViewById(R.id.swpVolumeList);
        lytVolumeEmptyList = (LinearLayout) view.findViewById(R.id.lytVolumeEmptyList);
        lytVolumeLoading = (LinearLayout) view.findViewById(R.id.lytVolumeLoading);
        lytVolumeError = (LinearLayout) view.findViewById(R.id.lytVolumeError);
        btnVolumeAdd = (FloatingActionButton) view.findViewById(R.id.btnVolumeAdd);
        btnVolumeErrorRefresh = (Button) view.findViewById(R.id.btnVolumeErrorRefresh);
        formularioVolume = new Intent(getActivity(), ColecaoVolumeFormActivity.class);
        formularioVolume.putExtra("COLECAO", colecao);

        requestQueue = Volley.newRequestQueue(getContext());
    }

    private void registrarEventos() {
        this.btnVolumeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(formularioVolume, ActivityRequestCodeEnum.VOLUME.getValue());
            }
        });
        this.swpVolumeList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                carregarVolumes();
            }
        });
        this.btnVolumeErrorRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarVolumes();
            }
        });
    }

    private void carregarVolumes() {
        String URL = getResources().getString(R.string.url_api) + "volume";
        URL += "?colecaoId=" + colecao.getId();

        final Fragment fragment = this;

        if (!swpVolumeList.isRefreshing()) {
            lytVolumeEmptyList.setVisibility(View.GONE);
            lytVolumeLoading.setVisibility(View.VISIBLE);
            rcvVolumeList.setVisibility(View.GONE);
            lytVolumeError.setVisibility(View.GONE);
        }

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Volume>>() {}.getType();
                ArrayList<Volume> dados = gson.fromJson(response, objectType);
                volumeAdapter = new VolumeAdapter(dados, colecao, fragment);
                volumeAdapter.notifyDataSetChanged();
                rcvVolumeList.setAdapter(volumeAdapter);

                if (swpVolumeList.isRefreshing()) {
                    swpVolumeList.setRefreshing(false);
                }

                if (dados.size() == 0) {
                    lytVolumeEmptyList.setVisibility(View.VISIBLE);
                    lytVolumeLoading.setVisibility(View.GONE);
                    rcvVolumeList.setVisibility(View.GONE);
                    lytVolumeError.setVisibility(View.GONE);
                } else {
                    lytVolumeEmptyList.setVisibility(View.GONE);
                    lytVolumeLoading.setVisibility(View.GONE);
                    rcvVolumeList.setVisibility(View.VISIBLE);
                    lytVolumeError.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (swpVolumeList.isRefreshing()) {
                    swpVolumeList.setRefreshing(false);
                }

                lytVolumeEmptyList.setVisibility(View.GONE);
                lytVolumeLoading.setVisibility(View.GONE);
                rcvVolumeList.setVisibility(View.GONE);
                lytVolumeError.setVisibility(View.VISIBLE);
            }
        });

        this.requestQueue.add(request);
    }
}