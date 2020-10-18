package com.example.mymangacollection.models;

import java.io.Serializable;

public class Serie implements Serializable {
    private String id;
    private String nome;

    public Serie() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
