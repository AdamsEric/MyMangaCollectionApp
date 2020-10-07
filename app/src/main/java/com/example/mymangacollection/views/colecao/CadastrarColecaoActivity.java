package com.example.mymangacollection.views.colecao;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mymangacollection.R;

import android.os.Bundle;

public class CadastrarColecaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Cadastrar coleção");
        setContentView(R.layout.activity_cadastrar_colecao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}