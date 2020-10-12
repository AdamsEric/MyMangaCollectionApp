package com.example.mymangacollection.views.editora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class CadastrarEditoraActivity extends AppCompatActivity {
    private Editora editora;
    private EditText edtAddEditoraNome;
    private Button btnAddEditoraGravar;

    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cadastrar editora");
        setContentView(R.layout.activity_cadastrar_editora);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editora = new Editora();

        this.registrarComponentes();
        this.registrarEventos();
    }

    private void registrarComponentes() {
        edtAddEditoraNome = (EditText) findViewById(R.id.edtAddEditoraNome);
        btnAddEditoraGravar = (Button) findViewById(R.id.btnAddEditoraGravar);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    private void registrarEventos() {
        btnAddEditoraGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            editora.setNome(edtAddEditoraNome.getText().toString());

            boolean cadastroValido = validarCadastro(editora);

            if (cadastroValido) {
                final String editoraJson = gson.toJson(editora);

                String URL = getResources().getString(R.string.url_api) + "editora";

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
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return editoraJson == null ? null : editoraJson.getBytes("utf-8");
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

    private boolean validarCadastro (Editora editora) {
        if (editora.getNome() == null) {
            Toast.makeText(this, "É necessário informar um nome", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}