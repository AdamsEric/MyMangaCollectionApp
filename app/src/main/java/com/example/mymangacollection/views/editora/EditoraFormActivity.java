package com.example.mymangacollection.views.editora;

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
import com.example.mymangacollection.models.Editora;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class EditoraFormActivity extends AppCompatActivity {
    private Editora editora;
    private EditText edtFormEditoraNome;
    private Button btnFormEditoraInserir;
    private Button btnFormEditoraAtualizar;
    private Button btnFormEditoraDeletar;
    private LinearLayout lytEditoraNovo;
    private LinearLayout lytEditoraEdicao;

    private RequestQueue requestQueue;
    private String apiURL;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Nova editora");
        setContentView(R.layout.activity_editora_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiURL = getResources().getString(R.string.url_api) + "editora";

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
        edtFormEditoraNome = (EditText) findViewById(R.id.edtFormEditoraNome);
        btnFormEditoraInserir = (Button) findViewById(R.id.btnFormEditoraInserir);
        btnFormEditoraAtualizar = (Button) findViewById(R.id.btnFormEditoraAtualizar);
        btnFormEditoraDeletar = (Button) findViewById(R.id.btnFormEditoraDeletar);
        lytEditoraNovo = (LinearLayout) findViewById(R.id.lytEditoraNovo);
        lytEditoraEdicao = (LinearLayout) findViewById(R.id.lytEditoraEdicao);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void registrarEventos() {
        btnFormEditoraInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editora.setNome(edtFormEditoraNome.getText().toString());

                boolean cadastroValido = validarCadastro();
                if (cadastroValido) {
                    gravarDados(Request.Method.POST);
                }
            }
        });

        btnFormEditoraAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editora.setNome(edtFormEditoraNome.getText().toString());

                boolean cadastroValido = validarCadastro();
                if (cadastroValido) {
                    gravarDados(Request.Method.PUT);
                }
            }
        });

        btnFormEditoraDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editora.getId() != null) {
                    new AlertDialog.Builder(EditoraFormActivity.this)
                            .setTitle("Sair")
                            .setMessage("Confirma a exclusão da editora " + editora.getNome() + " ?")
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
    }

    private boolean validarCadastro () {
        if (editora.getNome() == null) {
            Toast.makeText(this, "É necessário informar um nome", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void gravarDados (Integer requestMethod) {
        final String dadosJson = gson.toJson(editora);

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
        final String url = apiURL + "/" + editora.getId();

        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            if (error.networkResponse.statusCode == 404) {
                Toast.makeText(EditoraFormActivity.this,"O registro não foi encontrado.\nPor favor, atualize a lista.", Toast.LENGTH_LONG).show();
            }
            }
        });
        requestQueue.add(request);
    }

    private void verificarParametroEdicao() {
        Bundle bundle = getIntent().getExtras();
        editora = new Editora();

        if (bundle != null && bundle.containsKey("EDITORA")) {
            editora = (Editora) bundle.getSerializable("EDITORA");
            edtFormEditoraNome.setText(editora.getNome());
            lytEditoraNovo.setVisibility(View.GONE);
            lytEditoraEdicao.setVisibility(View.VISIBLE);

            this.setTitle("Editora: " + editora.getNome());
        } else {
            lytEditoraNovo.setVisibility(View.VISIBLE);
            lytEditoraEdicao.setVisibility(View.GONE);
        }
    }
}