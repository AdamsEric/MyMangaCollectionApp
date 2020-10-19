package com.example.mymangacollection.views.colecao;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.mymangacollection.models.ClassificacaoIndicativa;
import com.example.mymangacollection.models.Colecao;
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

public class ColecaoDadosFragment extends Fragment {
    private Colecao colecao;
    private EditText edtFormColecaoTitulo;
    private EditText edtFormColecaoTituloOriginal;
    private EditText edtFormColecaoTituloAlternativo;
    private EditText edtFormColecaoAutor;
    private EditText edtFormColecaoRoteirista;
    private EditText edtFormColecaoIlustrador;
    private Spinner spnFormEditora;
    private Spinner spnFormPeriodicidade;
    private Spinner spnFormSerie;
    private Spinner spnFormGenero;
    private Spinner spnFormClassificacaoIndicativa;
    private Spinner spnFormDemografia;
    private Button btnFormColecaoAtualizar;
    private Button btnFormColecaoDeletar;

    private RequestQueue requestQueue;
    private String apiURL;
    private Gson gson = new Gson();

    public ColecaoDadosFragment(Colecao colecao) {
        this.colecao = colecao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_colecao_dados, container, false);
        apiURL = getResources().getString(R.string.url_api) + "colecao";

        this.registrarComponentes(rootView);
        this.registrarEventos();
        this.definirDados();

        return rootView;
    }

    private void registrarComponentes(View view) {
        edtFormColecaoTitulo = (EditText) view.findViewById(R.id.edtFormColecaoTitulo);
        edtFormColecaoTituloOriginal = (EditText) view.findViewById(R.id.edtFormColecaoTituloOriginal);
        edtFormColecaoTituloAlternativo = (EditText) view.findViewById(R.id.edtFormColecaoTituloAlternativo);
        edtFormColecaoAutor = (EditText) view.findViewById(R.id.edtFormColecaoAutor);
        edtFormColecaoRoteirista = (EditText) view.findViewById(R.id.edtFormColecaoRoteirista);
        edtFormColecaoIlustrador = (EditText) view.findViewById(R.id.edtFormColecaoIlustrador);
        spnFormEditora = (Spinner) view.findViewById(R.id.spnFormEditora);
        spnFormPeriodicidade = (Spinner) view.findViewById(R.id.spnFormPeriodicidade);
        spnFormSerie = (Spinner) view.findViewById(R.id.spnFormSerie);
        spnFormGenero = (Spinner) view.findViewById(R.id.spnFormGenero);
        spnFormClassificacaoIndicativa = (Spinner) view.findViewById(R.id.spnFormClassificacaoIndicativa);
        spnFormDemografia = (Spinner) view.findViewById(R.id.spnFormDemografia);
        btnFormColecaoAtualizar = (Button) view.findViewById(R.id.btnFormColecaoAtualizar);
        btnFormColecaoDeletar = (Button) view.findViewById(R.id.btnFormColecaoDeletar);

        this.requestQueue = Volley.newRequestQueue(getContext());

        this.carregarEditoras(view);
        this.carregarPeriodicidade(view);
        this.carregarSeries(view);
        this.carregarGeneros(view);
        this.carregarClassificacoesIndicativas(view);
        this.carregarDemografias(view);
    }

    private void definirDados() {
        edtFormColecaoTitulo.setText(colecao.getTitulo());
        edtFormColecaoTituloOriginal.setText(colecao.getTituloOriginal());
        edtFormColecaoTituloAlternativo.setText(colecao.getTituloAlternativo());
        edtFormColecaoAutor.setText(colecao.getAutor());
        edtFormColecaoRoteirista.setText(colecao.getRoteirista());
        edtFormColecaoIlustrador.setText(colecao.getIlustrador());
    }

    private void registrarEventos() {
        btnFormColecaoAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colecao.setTitulo(edtFormColecaoTitulo.getText().toString());
                colecao.setTituloOriginal(edtFormColecaoTituloOriginal.getText().toString());
                colecao.setTituloAlternativo(edtFormColecaoTituloAlternativo.getText().toString());
                colecao.setAutor(edtFormColecaoAutor.getText().toString());
                colecao.setRoteirista(edtFormColecaoRoteirista.getText().toString());
                colecao.setIlustrador(edtFormColecaoIlustrador.getText().toString());

                boolean cadastroValido = validarCadastro();
                if (cadastroValido) {
                    atualizarDados();
                }
            }
        });

        btnFormColecaoDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colecao.getId() != null) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Sair")
                            .setMessage("Confirma a exclusão da coleção " + colecao.getTitulo() + " ?")
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

        spnFormEditora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Editora editora = (Editora) parent.getItemAtPosition(position);
                colecao.setEditoraId(editora.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnFormPeriodicidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Periodicidade periodicidade = (Periodicidade) parent.getItemAtPosition(position);
                colecao.setPeriodicidadeId(periodicidade.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnFormSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Serie serie = (Serie) parent.getItemAtPosition(position);
                colecao.setSerieId(serie.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnFormGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Genero genero = (Genero) parent.getItemAtPosition(position);
                colecao.setGeneroId(genero.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnFormClassificacaoIndicativa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClassificacaoIndicativa classificacao = (ClassificacaoIndicativa) parent.getItemAtPosition(position);
                colecao.setClassificacaoIndicativaId(classificacao.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spnFormDemografia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Demografia demografia = (Demografia) parent.getItemAtPosition(position);
                colecao.setDemografiaId(demografia.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private boolean validarCadastro () {
        if (colecao.getTitulo() == null) {
            Toast.makeText(getContext(), "É necessário informar um título", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (colecao.getAutor() == null) {
            Toast.makeText(getContext(), "É necessário informar um autor", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (colecao.getEditoraId() == null) {
            Toast.makeText(getContext(), "É necessário informar uma editora", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void atualizarDados () {
        final String dadosJson = gson.toJson(colecao);

        StringRequest request = new StringRequest(Request.Method.PUT, apiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getActivity().onBackPressed();
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
        final String url = apiURL + "/" + colecao.getId();

        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getActivity().onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse.statusCode == 404) {
                    Toast.makeText(getContext(),"O registro não foi encontrado.\nPor favor, atualize a lista.", Toast.LENGTH_LONG).show();
                }
            }
        });
        requestQueue.add(request);
    }


    public void carregarEditoras (View rootView) {
        String URL = getResources().getString(R.string.url_api) + "editora";
        final View view = rootView;

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Editora>>() {}.getType();
                ArrayList<Editora> dados = gson.fromJson(response, objectType);

                if (dados.size() == 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Editoras não cadastradas")
                            .setMessage("Você não possui editoras cadastradas.\n" +
                                    "Por favor, realize o cadastro primeiro para então cadastrar as coleções")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    ArrayAdapter<Editora> spinnerAdapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, dados);

                    int defaultPosition = 0;
                    if (colecao.getEditoraId() != null) {
                        for (int i = 0; i < dados.size(); i++) {
                            if (colecao.getEditoraId().equals(dados.get(i).getId())) {
                                defaultPosition = i;
                            }
                        }
                    }

                    spnFormEditora = (Spinner) view.findViewById(R.id.spnFormEditora);
                    spnFormEditora.setAdapter(spinnerAdapter);
                    spnFormEditora.setSelection(defaultPosition);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    private void carregarPeriodicidade(View rootView) {
        String URL = getResources().getString(R.string.url_api) + "periodicidade";
        final View view = rootView;

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Periodicidade>>() {}.getType();
                ArrayList<Periodicidade> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Periodicidade> spinnerAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, dados);

                int defaultPosition = 0;
                if (colecao.getPeriodicidadeId() != null) {
                    for (int i = 0; i < dados.size(); i++) {
                        if (colecao.getPeriodicidadeId().equals(dados.get(i).getId())) {
                            defaultPosition = i;
                        }
                    }
                }

                spnFormPeriodicidade = (Spinner) view.findViewById(R.id.spnFormPeriodicidade);
                spnFormPeriodicidade.setAdapter(spinnerAdapter);
                spnFormPeriodicidade.setSelection(defaultPosition);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    private void carregarGeneros(View rootView) {
        String URL = getResources().getString(R.string.url_api) + "genero";
        final View view = rootView;

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Genero>>() {}.getType();
                ArrayList<Genero> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Genero> spinnerAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, dados);

                int defaultPosition = 0;
                if (colecao.getGeneroId() != null) {
                    for (int i = 0; i < dados.size(); i++) {
                        if (colecao.getGeneroId().equals(dados.get(i).getId())) {
                            defaultPosition = i;
                        }
                    }
                }

                spnFormGenero = (Spinner) view.findViewById(R.id.spnFormGenero);
                spnFormGenero.setAdapter(spinnerAdapter);
                spnFormGenero.setSelection(defaultPosition);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    private void carregarSeries(View rootView) {
        String URL = getResources().getString(R.string.url_api) + "serie";
        final View view = rootView;

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Serie>>() {}.getType();
                ArrayList<Serie> dados = gson.fromJson(response, objectType);

                if (dados.size() == 0) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Séries não cadastradas")
                            .setMessage("Você não possui séries cadastradas.\n" +
                                    "Por favor, realize o cadastro primeiro para então cadastrar as coleções.")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    ArrayAdapter<Serie> spinnerAdapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, dados);

                    int defaultPosition = 0;
                    if (colecao.getSerieId() != null) {
                        for (int i = 0; i < dados.size(); i++) {
                            if (colecao.getSerieId().equals(dados.get(i).getId())) {
                                defaultPosition = i;
                            }
                        }
                    }

                    spnFormSerie = (Spinner) view.findViewById(R.id.spnFormSerie);
                    spnFormSerie.setAdapter(spinnerAdapter);
                    spnFormSerie.setSelection(defaultPosition);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    public void carregarClassificacoesIndicativas (View rootView) {
        String URL = getResources().getString(R.string.url_api) + "classificacao_indicativa";
        final View view = rootView;

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<ClassificacaoIndicativa>>() {}.getType();
                ArrayList<ClassificacaoIndicativa> dados = gson.fromJson(response, objectType);
                ArrayAdapter<ClassificacaoIndicativa> spinnerAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, dados);

                int defaultPosition = 0;
                if (colecao.getClassificacaoIndicativaId() != null) {
                    for (int i = 0; i < dados.size(); i++) {
                        if (colecao.getClassificacaoIndicativaId().equals(dados.get(i).getId())) {
                            defaultPosition = i;
                        }
                    }
                }

                spnFormClassificacaoIndicativa = (Spinner) view.findViewById(R.id.spnFormClassificacaoIndicativa);
                spnFormClassificacaoIndicativa.setAdapter(spinnerAdapter);
                spnFormClassificacaoIndicativa.setSelection(defaultPosition);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        this.requestQueue.add(request);
    }

    public void carregarDemografias (View rootView) {
        String URL = getResources().getString(R.string.url_api) + "demografia";
        final View view = rootView;

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Type objectType = new TypeToken<ArrayList<Demografia>>() {}.getType();
                ArrayList<Demografia> dados = gson.fromJson(response, objectType);
                ArrayAdapter<Demografia> spinnerAdapter = new ArrayAdapter<>(getContext(),
                        android.R.layout.simple_spinner_dropdown_item, dados);

                int defaultPosition = 0;
                if (colecao.getDemografiaId() != null) {
                    for (int i = 0; i < dados.size(); i++) {
                        if (colecao.getDemografiaId().equals(dados.get(i).getId())) {
                            defaultPosition = i;
                        }
                    }
                }

                spnFormDemografia = (Spinner) view.findViewById(R.id.spnFormDemografia);
                spnFormDemografia.setAdapter(spinnerAdapter);
                spnFormDemografia.setSelection(defaultPosition);
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