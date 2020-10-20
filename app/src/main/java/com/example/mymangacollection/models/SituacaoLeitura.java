package com.example.mymangacollection.models;

import java.io.Serializable;

public class SituacaoLeitura implements Serializable {
    private String id;
    private String ordem;
    private String descricao;
    private boolean stConcluido;

    public SituacaoLeitura() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isStConcluido() {
        return stConcluido;
    }

    public void setStConcluido(boolean stConcluido) {
        this.stConcluido = stConcluido;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
