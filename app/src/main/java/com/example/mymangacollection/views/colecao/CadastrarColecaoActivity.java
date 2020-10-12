package com.example.mymangacollection.views.colecao;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymangacollection.R;
import com.example.mymangacollection.http.HttpHelper;
import com.example.mymangacollection.models.ClassificacaoIndicativa;
import com.example.mymangacollection.models.Colecao;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mymangacollection.models.Demografia;
import com.example.mymangacollection.models.Editora;
import com.example.mymangacollection.models.Genero;
import com.example.mymangacollection.models.Periodicidade;
import com.example.mymangacollection.models.Serie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CadastrarColecaoActivity extends AppCompatActivity {
    Colecao colecao;
    EditText edtAddColecaoTitulo;
    EditText edtAddColecaoTituloOriginal;
    EditText edtAddColecaoTituloAlternativo;
    EditText edtAddColecaoAutor;
    EditText edtAddColecaoRoteirista;
    EditText edtAddColecaoIlustrador;
    Spinner spnAddEditora;
    Spinner spnAddPeriodicidade;
    Spinner spnAddSerie;
    Spinner spnAddGenero;
    Spinner spnAddClassificacaoIndicativa;
    Spinner spnAddDemografia;
    Button btnAddColecaoGravar;

    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cadastrar coleção");
        setContentView(R.layout.activity_cadastrar_colecao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        colecao = new Colecao();

        this.registrarComponentes();
        this.registrarEventos();
    }

    private void registrarComponentes() {
        edtAddColecaoTitulo = findViewById(R.id.edtAddColecaoTitulo);
        edtAddColecaoTituloOriginal = findViewById(R.id.edtAddColecaoTituloOriginal);
        edtAddColecaoTituloAlternativo = findViewById(R.id.edtAddColecaoTituloAlternativo);
        edtAddColecaoAutor = findViewById(R.id.edtAddColecaoAutor);
        edtAddColecaoRoteirista = findViewById(R.id.edtAddColecaoRoteirista);
        edtAddColecaoIlustrador = findViewById(R.id.edtAddColecaoIlustrador);
        spnAddEditora = findViewById(R.id.spnAddEditora);
        spnAddPeriodicidade = findViewById(R.id.spnAddPeriodicidade);
        spnAddSerie = findViewById(R.id.spnAddSerie);
        spnAddGenero = findViewById(R.id.spnAddGenero);
        spnAddClassificacaoIndicativa = findViewById(R.id.spnAddClassificacaoIndicativa);
        spnAddDemografia = findViewById(R.id.spnAddDemografia);
        btnAddColecaoGravar = findViewById(R.id.btnAddColecaoGravar);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());

        this.carregarEditoras();
        this.carregarPeriodicidade();
        this.carregarSeries();
        this.carregarGeneros();
        this.carregarClassificacoesIndicativas();
        this.carregarDemografias();
    }

    private void registrarEventos() {
        spnAddEditora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Editora editora = (Editora) parent.getItemAtPosition(position);
                colecao.setEditoraId(editora.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnAddPeriodicidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Periodicidade periodicidade = (Periodicidade) parent.getItemAtPosition(position);
                colecao.setPeriodicidadeId(periodicidade.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnAddGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Serie serie = (Serie) parent.getItemAtPosition(position);
                colecao.setSerieId(serie.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnAddGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Genero genero = (Genero) parent.getItemAtPosition(position);
                colecao.setGeneroId(genero.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnAddClassificacaoIndicativa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClassificacaoIndicativa classificacao = (ClassificacaoIndicativa) parent.getItemAtPosition(position);
                colecao.setClassificacaoIndicativaId(classificacao.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnAddDemografia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Demografia demografia = (Demografia) parent.getItemAtPosition(position);
                colecao.setDemografiaId(demografia.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnAddColecaoGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colecao.setTitulo(edtAddColecaoTitulo.getText().toString());
                colecao.setTituloOriginal(edtAddColecaoTituloOriginal.getText().toString());
                colecao.setTituloAlternativo(edtAddColecaoTituloAlternativo.getText().toString());
                colecao.setAutor(edtAddColecaoAutor.getText().toString());
                colecao.setRoteirista(edtAddColecaoRoteirista.getText().toString());
                colecao.setIlustrador(edtAddColecaoIlustrador.getText().toString());

                boolean cadastroValido = validarCadastro(colecao);

                if (cadastroValido) {
                    final String colecaoJson = gson.toJson(colecao);

                    String URL = getResources().getString(R.string.url_api) + "colecao";

                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(CadastrarColecaoActivity.this, "Cadastro realizado", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return colecaoJson == null ? null : colecaoJson.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                return null;
                            }
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
    }

    private boolean validarCadastro (Colecao colecao) {
        if (colecao.getTitulo() == null) {
            Toast.makeText(this, "É necessário informar um título", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (colecao.getAutor() == null) {
            Toast.makeText(this, "É necessário informar um autor", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (colecao.getEditoraId() == null) {
            Toast.makeText(this, "É necessário informar uma editora", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void carregarEditoras () {
        String URL = getResources().getString(R.string.url_api) + "editora";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Editora>>() {}.getType();
                ArrayList<Editora> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Editora> spinnerAdapter = new ArrayAdapter<>(CadastrarColecaoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, dados);

                spnAddEditora = (Spinner) findViewById(R.id.spnAddEditora);
                spnAddEditora.setAdapter(spinnerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    private void carregarPeriodicidade() {
        String URL = getResources().getString(R.string.url_api) + "periodicidade";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Periodicidade>>() {}.getType();
                ArrayList<Periodicidade> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Periodicidade> spinnerAdapter = new ArrayAdapter<>(CadastrarColecaoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, dados);

                spnAddPeriodicidade = (Spinner) findViewById(R.id.spnAddPeriodicidade);
                spnAddPeriodicidade.setAdapter(spinnerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    private void carregarGeneros() {
        String URL = getResources().getString(R.string.url_api) + "genero";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Genero>>() {}.getType();
                ArrayList<Genero> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Genero> spinnerAdapter = new ArrayAdapter<>(CadastrarColecaoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, dados);

                spnAddGenero = (Spinner) findViewById(R.id.spnAddGenero);
                spnAddGenero.setAdapter(spinnerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    private void carregarSeries() {
        String URL = getResources().getString(R.string.url_api) + "serie";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Serie>>() {}.getType();
                ArrayList<Serie> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Serie> spinnerAdapter = new ArrayAdapter<>(CadastrarColecaoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, dados);

                spnAddSerie = (Spinner) findViewById(R.id.spnAddSerie);
                spnAddSerie.setAdapter(spinnerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    public void carregarClassificacoesIndicativas () {
        String URL = getResources().getString(R.string.url_api) + "classificacao_indicativa";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<ClassificacaoIndicativa>>() {}.getType();
                ArrayList<ClassificacaoIndicativa> dados = gson.fromJson(response, objectType);
                ArrayAdapter<ClassificacaoIndicativa> spinnerAdapter = new ArrayAdapter<>(CadastrarColecaoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, dados);

                spnAddClassificacaoIndicativa = (Spinner) findViewById(R.id.spnAddClassificacaoIndicativa);
                spnAddClassificacaoIndicativa.setAdapter(spinnerAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    public void carregarDemografias () {
        String URL = getResources().getString(R.string.url_api) + "demografia";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Demografia>>() {}.getType();
                ArrayList<Demografia> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Demografia> spinnerAdapter = new ArrayAdapter<>(CadastrarColecaoActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, dados);

                spnAddDemografia = (Spinner) findViewById(R.id.spnAddDemografia);
                spnAddDemografia.setAdapter(spinnerAdapter);
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