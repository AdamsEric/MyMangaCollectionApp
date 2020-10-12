package com.example.mymangacollection.models;

public class Periodicidade {
    private String id;
    private String descricao;
    private Integer quantidadeDias;

    public Periodicidade() {
    }

    @Override
    public String toString() {
        return descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(Integer quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }
}
