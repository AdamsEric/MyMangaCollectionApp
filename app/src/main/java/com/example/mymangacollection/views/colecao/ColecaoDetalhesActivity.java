package com.example.mymangacollection.views.colecao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.example.mymangacollection.R;
import com.example.mymangacollection.adapters.ColecaoPagerAdapter;
import com.example.mymangacollection.models.Colecao;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;

public class ColecaoDetalhesActivity extends AppCompatActivity {
    private TabLayout tlyColecao;
    private ViewPager2 vwpColecao;

    private Colecao colecao;

    private RequestQueue requestQueue;
    private String apiURL;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_colecao_detalhes);
        colecao = new Colecao();
        apiURL = getResources().getString(R.string.url_api) + "colecao";

        registrarComponentes();
        registrarEventos();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void registrarComponentes() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("COLECAO")) {
            colecao = (Colecao) bundle.getSerializable("COLECAO");
            this.setTitle("Coleção: " + colecao.getTitulo());
        }

        tlyColecao = findViewById(R.id.tlyColecao);
        vwpColecao = findViewById(R.id.vwpColecao);

        ColecaoPagerAdapter colecaoPagerAdapter = new ColecaoPagerAdapter(this, colecao);
        vwpColecao.setAdapter(colecaoPagerAdapter);
    }

    private void registrarEventos() {
        final TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tlyColecao, vwpColecao, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0: {
                        tab.setText("Dados");
                        break;
                    }
                    case 1: {
                        tab.setText("Volumes");
                        if (colecao != null && colecao.getQuantidadeVolumesAdquiridos() > 0) {
                            BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                            badgeDrawable.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
                            badgeDrawable.setVisible(true);
                            badgeDrawable.setNumber(colecao.getQuantidadeVolumesAdquiridos());
                        }
                        break;
                    }
                }
            }
        });
        tabLayoutMediator.attach();
    }
}