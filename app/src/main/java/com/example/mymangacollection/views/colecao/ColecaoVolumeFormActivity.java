package com.example.mymangacollection.views.colecao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymangacollection.R;
import com.example.mymangacollection.models.Colecao;
import com.example.mymangacollection.models.SituacaoLeitura;
import com.example.mymangacollection.models.Volume;
import com.google.gson.Gson;
import com.example.mymangacollection.helpers.StringHelper;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ColecaoVolumeFormActivity extends AppCompatActivity {
    private Colecao colecao;
    private Volume volume;

    private EditText edtFormVolumeNumero;
    private EditText edtFormVolumePreco;
    private EditText edtFormVolumeQtdePaginas;
    private EditText edtFormVolumeQtdeExemplares;
    private EditText edtFormVolumeUltimoCapLido;
    private EditText edtFormVolumeCapInicial;
    private EditText edtFormVolumeCapFinal;
    private EditText edtFormVolumeDescricao;
    private EditText edtFormVolumeObservacoes;
    private Spinner spnFormSituacaoLeitura;
    private Button btnFormVolumeInserir;
    private Button btnFormVolumeAtualizar;
    private Button btnFormVolumeDeletar;
    private LinearLayout lytVolumeNovo;
    private LinearLayout lytVolumeEdicao;

    private RequestQueue requestQueue;
    private String apiURL;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colecao_volume_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiURL = getResources().getString(R.string.url_api) + "volume";

        this.registrarComponentes();
        this.registrarEventos();
        this.verificarParametroEdicao();

        this.carregarSituacoesLeitura();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void registrarComponentes() {
        edtFormVolumeNumero = (EditText) findViewById(R.id.edtFormVolumeNumero);
        edtFormVolumePreco = (EditText) findViewById(R.id.edtFormVolumePreco);
        edtFormVolumeQtdePaginas = (EditText) findViewById(R.id.edtFormVolumeQtdePaginas);
        edtFormVolumeQtdeExemplares = (EditText) findViewById(R.id.edtFormVolumeQtdeExemplares);
        edtFormVolumeUltimoCapLido = (EditText) findViewById(R.id.edtFormVolumeUltimoCapLido);
        edtFormVolumeCapInicial = (EditText) findViewById(R.id.edtFormVolumeCapInicial);
        edtFormVolumeCapFinal = (EditText) findViewById(R.id.edtFormVolumeCapFinal);
        edtFormVolumeDescricao = (EditText) findViewById(R.id.edtFormVolumeDescricao);
        edtFormVolumeObservacoes = (EditText) findViewById(R.id.edtFormVolumeObservacoes);
        spnFormSituacaoLeitura = (Spinner) findViewById(R.id.spnFormSituacaoLeitura);
        btnFormVolumeInserir = (Button) findViewById(R.id.btnFormVolumeInserir);
        btnFormVolumeAtualizar = (Button) findViewById(R.id.btnFormVolumeAtualizar);
        btnFormVolumeDeletar = (Button) findViewById(R.id.btnFormVolumeDeletar);
        lytVolumeNovo = (LinearLayout) findViewById(R.id.lytVolumeNovo);
        lytVolumeEdicao = (LinearLayout) findViewById(R.id.lytVolumeEdicao);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void registrarEventos() {
        btnFormVolumeInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume.setNumero(StringHelper.stringToInteger(edtFormVolumeNumero.getText().toString()));
                volume.setQuantidadePaginas(StringHelper.stringToInteger(edtFormVolumeQtdePaginas.getText().toString()));
                volume.setQuantidadeExemplares(StringHelper.stringToInteger(edtFormVolumeQtdeExemplares.getText().toString()));
                volume.setCapituloInicial(StringHelper.stringToInteger(edtFormVolumeCapInicial.getText().toString()));
                volume.setCapituloFinal(StringHelper.stringToInteger(edtFormVolumeCapFinal.getText().toString()));
                volume.setUltimoCapituloLido(StringHelper.stringToInteger(edtFormVolumeUltimoCapLido.getText().toString()));
                volume.setPrecoCapa(StringHelper.stringToFloat(edtFormVolumePreco.getText().toString()));
                volume.setDescricao(edtFormVolumeDescricao.getText().toString());
                volume.setObservacoes(edtFormVolumeObservacoes.getText().toString());

                boolean cadastroValido = validarCadastro();
                if (cadastroValido) {
                    gravarDados(Request.Method.POST);
                }
            }
        });

        btnFormVolumeAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volume.setNumero(StringHelper.stringToInteger(edtFormVolumeNumero.getText().toString()));
                volume.setQuantidadePaginas(StringHelper.stringToInteger(edtFormVolumeQtdePaginas.getText().toString()));
                volume.setQuantidadeExemplares(StringHelper.stringToInteger(edtFormVolumeQtdeExemplares.getText().toString()));
                volume.setCapituloInicial(StringHelper.stringToInteger(edtFormVolumeCapInicial.getText().toString()));
                volume.setCapituloFinal(StringHelper.stringToInteger(edtFormVolumeCapFinal.getText().toString()));
                volume.setUltimoCapituloLido(StringHelper.stringToInteger(edtFormVolumeUltimoCapLido.getText().toString()));
                volume.setPrecoCapa(StringHelper.stringToFloat(edtFormVolumePreco.getText().toString()));
                volume.setDescricao(edtFormVolumeDescricao.getText().toString());
                volume.setObservacoes(edtFormVolumeObservacoes.getText().toString());

                boolean cadastroValido = validarCadastro();
                if (cadastroValido) {
                    gravarDados(Request.Method.PUT);
                }
            }
        });

        btnFormVolumeDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (volume.getId() != null) {
                    new AlertDialog.Builder(ColecaoVolumeFormActivity.this)
                            .setTitle("Sair")
                            .setMessage("Confirma a exclusão do volume " + volume.getNumero().toString() + " ?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which) { excluirRegistro();
                                }

                            })
                            .setNegativeButton("Não", null)
                            .show();
                }
            }
        });

        spnFormSituacaoLeitura.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SituacaoLeitura situacaoLeitura = (SituacaoLeitura) parent.getItemAtPosition(position);
                volume.setSituacaoLeituraId(situacaoLeitura.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private boolean validarCadastro () {
        if (volume.getNumero() == null) {
            Toast.makeText(this, "É necessário informar o número do volume", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (volume.getQuantidadeExemplares() == null) {
            Toast.makeText(this, "É necessário informar a quantidade de exemplares que você possui",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void gravarDados (Integer requestMethod) {
        final String dadosJson = gson.toJson(volume);

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
                    return dadosJson == null ? null : dadosJson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }
        };
        requestQueue.add(request);
    }

    private void excluirRegistro () {
        final String url = apiURL + "/" + volume.getId();

        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(ColecaoVolumeFormActivity.this,
                            "O registro não foi encontrado.\nPor favor, atualize a lista.", Toast.LENGTH_LONG).show();
                }
            }
        });
        requestQueue.add(request);
    }

    private void verificarParametroEdicao() {
        Bundle bundle = getIntent().getExtras();
        volume = new Volume();

        if (bundle != null) {
            if (bundle.containsKey("COLECAO")) {
                colecao = (Colecao) bundle.getSerializable("COLECAO");
                volume.setColecaoId(colecao.getId());
            } else {
                onBackPressed();
                Toast.makeText(this, "Existe um erro com a coleção", Toast.LENGTH_SHORT).show();
            }

            if (bundle.containsKey("VOLUME")) {
                volume = (Volume) bundle.getSerializable("VOLUME");

                edtFormVolumeNumero.setText(StringHelper.integerToString(volume.getNumero()));
                edtFormVolumePreco.setText(StringHelper.floatToString(volume.getPrecoCapa()));
                edtFormVolumeDescricao.setText(volume.getDescricao());
                edtFormVolumeObservacoes.setText(volume.getObservacoes());
                edtFormVolumeQtdePaginas.setText(StringHelper.integerToString(volume.getQuantidadePaginas()));
                edtFormVolumeQtdeExemplares.setText(StringHelper.integerToString(volume.getQuantidadeExemplares()));
                edtFormVolumeCapInicial.setText(StringHelper.integerToString(volume.getCapituloInicial()));
                edtFormVolumeCapFinal.setText(StringHelper.integerToString(volume.getCapituloFinal()));
                edtFormVolumeUltimoCapLido.setText(StringHelper.integerToString(volume.getUltimoCapituloLido()));

                lytVolumeNovo.setVisibility(View.GONE);
                lytVolumeEdicao.setVisibility(View.VISIBLE);

                this.setTitle("Volume " + volume.getNumero() + ": " + colecao.getTitulo());
            } else {
                this.setTitle("Novo volume: " + colecao.getTitulo());
                lytVolumeNovo.setVisibility(View.VISIBLE);
                lytVolumeEdicao.setVisibility(View.GONE);
            }
        } else {
            Toast.makeText(this, "Existe um erro com a coleção", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    public void carregarSituacoesLeitura () {
        String URL = getResources().getString(R.string.url_api) + "situacao_leitura";

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<SituacaoLeitura>>() {}.getType();
                ArrayList<SituacaoLeitura> dados = gson.fromJson(response, objectType);
                ArrayAdapter<SituacaoLeitura> spinnerAdapter = new ArrayAdapter<>(ColecaoVolumeFormActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, dados);

                int defaultPosition = 0;
                if (volume.getSituacaoLeituraId() != null) {
                    for (int i = 0; i < dados.size(); i++) {
                        if (volume.getSituacaoLeituraId().equals(dados.get(i).getId())) {
                            defaultPosition = i;
                        }
                    }
                }

                spnFormSituacaoLeitura = (Spinner) findViewById(R.id.spnFormSituacaoLeitura);
                spnFormSituacaoLeitura.setAdapter(spinnerAdapter);
                spnFormSituacaoLeitura.setSelection(defaultPosition);
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