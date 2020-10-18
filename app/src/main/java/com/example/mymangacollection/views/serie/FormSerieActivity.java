package com.example.mymangacollection.views.serie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymangacollection.R;
import com.example.mymangacollection.models.Serie;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class FormSerieActivity extends AppCompatActivity {
    private Serie serie;
    private EditText edtFormSerieNome;
    private Button btnFormSerieInserir;
    private Button btnFormSerieAtualizar;
    private Button btnFormSerieDeletar;
    private LinearLayout lytSerieNovo;
    private LinearLayout lytSerieEdicao;

    private RequestQueue requestQueue;
    private String apiURL;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cadastrar série");
        setContentView(R.layout.activity_form_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        serie = new Serie();
        apiURL = getResources().getString(R.string.url_api) + "serie";

        this.registrarComponentes();
        this.registrarEventos();
        this.verificarParametroEdicao();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void registrarComponentes() {
        edtFormSerieNome = (EditText) findViewById(R.id.edtFormSerieNome);
        btnFormSerieInserir = (Button) findViewById(R.id.btnFormSerieInserir);
        btnFormSerieAtualizar = (Button) findViewById(R.id.btnFormSerieAtualizar);
        btnFormSerieDeletar = (Button) findViewById(R.id.btnFormSerieDeletar);
        lytSerieNovo = (LinearLayout) findViewById(R.id.lytSerieNovo);
        lytSerieEdicao = (LinearLayout) findViewById(R.id.lytSerieEdicao);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void registrarEventos() {
        btnFormSerieInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            serie.setNome(edtFormSerieNome.getText().toString());

            boolean cadastroValido = validarCadastro();
            if (cadastroValido) {
                gravarDados(Request.Method.POST);
            }
            }
        });

        btnFormSerieAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serie.setNome(edtFormSerieNome.getText().toString());

                boolean cadastroValido = validarCadastro();
                if (cadastroValido) {
                    gravarDados(Request.Method.PUT);
                }
            }
        });

        btnFormSerieDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serie.getId() != null) {
                    new AlertDialog.Builder(FormSerieActivity.this)
                            .setTitle("Sair")
                            .setMessage("Confirma a exclusão da série " + serie.getNome() + " ?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    excluirRegistro();
                                }

                            })
                            .setNegativeButton("Não", null)
                            .show();
                }
            }
        });
    }

    private boolean validarCadastro () {
        if (serie.getNome() == null) {
            Toast.makeText(this, "É necessário informar um nome", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void gravarDados (Integer requestMethod) {
        final String serieJson = gson.toJson(serie);

        StringRequest request = new StringRequest(requestMethod, apiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onBackPressed();
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
            public byte[] getBody() {
                try {
                    return serieJson == null ? null : serieJson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        requestQueue.add(request);
    }

    private void excluirRegistro () {
        final String url = apiURL + "/" + serie.getId();

        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(FormSerieActivity.this,"O registro não foi encontrado.\nPor favor, atualize a lista.", Toast.LENGTH_LONG).show();
                }
            }
        });
        requestQueue.add(request);
    }

    private void verificarParametroEdicao() {
        Bundle bundle = getIntent().getExtras();
        serie = new Serie();

        if (bundle != null && bundle.containsKey("SERIE")) {
            this.setTitle("Editar série");
            serie = (Serie) bundle.getSerializable("SERIE");
            edtFormSerieNome.setText(serie.getNome());
            lytSerieNovo.setVisibility(View.GONE);
            lytSerieEdicao.setVisibility(View.VISIBLE);
        } else {
            lytSerieNovo.setVisibility(View.VISIBLE);
            lytSerieEdicao.setVisibility(View.GONE);
        }
    }
}