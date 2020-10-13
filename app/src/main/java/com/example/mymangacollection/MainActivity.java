package com.example.mymangacollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymangacollection.models.ClassificacaoIndicativa;
import com.example.mymangacollection.views.colecao.ColecaoFragment;
import com.example.mymangacollection.views.editora.EditoraFragment;
import com.example.mymangacollection.views.serie.SerieFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    Spinner spnAddEditora;
    Spinner spnAddPeriodicidade;
    Spinner spnAddSerie;
    Spinner spnAddGenero;
    Spinner spnAddClassificacaoIndicativa;
    Spinner spnAddDemografia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ColecaoFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_colecao);
        }

        this.carregarValoresPadroes();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_colecao:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ColecaoFragment()).commit();
                break;
            case R.id.nav_editora:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EditoraFragment()).commit();
                break;
            case R.id.nav_serie:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SerieFragment()).commit();
                break;
            case R.id.sair:
                new AlertDialog.Builder(this)
                    .setTitle("Sair")
                    .setMessage("Deseja realmente sair do app?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer((GravityCompat.START));
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Sair")
                    .setMessage("Deseja realmente sair do app?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
    }

    public void carregarValoresPadroes () {
        spnAddEditora = findViewById(R.id.spnAddEditora);
        spnAddPeriodicidade = findViewById(R.id.spnAddPeriodicidade);
        spnAddSerie = findViewById(R.id.spnAddSerie);
        spnAddGenero = findViewById(R.id.spnAddGenero);

        spnAddDemografia = findViewById(R.id.spnAddDemografia);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void carregarClassificacoes () {
        String URL = R.string.url_api + "classificacao_indicativa";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type classificacaoType = new TypeToken<ArrayList<ClassificacaoIndicativa>>() {
                }.getType();

                ArrayList<ClassificacaoIndicativa> classificacoes = gson.fromJson(response, classificacaoType);
                ArrayAdapter<ClassificacaoIndicativa> spinnerAdapter = new ArrayAdapter<ClassificacaoIndicativa>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, classificacoes);
                spnAddClassificacaoIndicativa = (Spinner) findViewById(R.id.spnAddClassificacaoIndicativa);
                System.out.println(spnAddClassificacaoIndicativa);
               // spnAddClassificacaoIndicativa.setAdapter(spinnerAdapter);
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