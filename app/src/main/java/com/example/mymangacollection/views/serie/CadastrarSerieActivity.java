package com.example.mymangacollection.views.serie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CadastrarSerieActivity extends AppCompatActivity {
    private Serie serie;
    private EditText edtAddSerieNome;
    private Button btnAddSerieGravar;

    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cadastrar série");
        setContentView(R.layout.activity_cadastrar_serie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        serie = new Serie();

        this.registrarComponentes();
        this.registrarEventos();
    }

    private void registrarComponentes() {
        edtAddSerieNome = (EditText) findViewById(R.id.edtAddSerieNome);
        btnAddSerieGravar = (Button) findViewById(R.id.btnAddSerieGravar);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void registrarEventos() {
        btnAddSerieGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            serie.setNome(edtAddSerieNome.getText().toString());

            boolean cadastroValido = validarCadastro(serie);

            if (cadastroValido) {
                final String serieJson = gson.toJson(serie);

                String URL = getResources().getString(R.string.url_api) + "serie";

                StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
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
            }
        });
    }

    private boolean validarCadastro (Serie serie) {
        if (serie.getNome() == null) {
            Toast.makeText(this, "É necessário informar um nome", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}