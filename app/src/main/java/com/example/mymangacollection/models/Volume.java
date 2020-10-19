package com.example.mymangacollection.models;

import java.io.Serializable;

public class Volume implements Serializable {
    private String id;
    private String colecaoId;
    private Integer numero;
    private String descricao;
    private float precoCapa;
    private Integer quantidadePaginas;
    private Integer capituloInicial;
    private Integer capituloFinal;
    private SituacaoLeitura situacaoLeitura;
    private Integer ultimoCapituloLido;
    private Integer quantidadeExemplares;
    private Integer observacoes;

    public Volume() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColecaoId() {
        return colecaoId;
    }

    public void setColecaoId(String colecaoId) {
        this.colecaoId = colecaoId;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPrecoCapa() {
        return precoCapa;
    }

    public void setPrecoCapa(float precoCapa) {
        this.precoCapa = precoCapa;
    }

    public Integer getQuantidadePaginas() {
        return quantidadePaginas;
    }

    public void setQuantidadePaginas(Integer quantidadePaginas) {
        this.quantidadePaginas = quantidadePaginas;
    }

    public Integer getCapituloInicial() {
        return capituloInicial;
    }

    public void setCapituloInicial(Integer capituloInicial) {
        this.capituloInicial = capituloInicial;
    }

    public Integer getCapituloFinal() {
        return capituloFinal;
    }

    public void setCapituloFinal(Integer capituloFinal) {
        this.capituloFinal = capituloFinal;
    }

    public SituacaoLeitura getSituacaoLeitura() {
        return situacaoLeitura;
    }

    public void setSituacaoLeitura(SituacaoLeitura situacaoLeitura) {
        this.situacaoLeitura = situacaoLeitura;
    }

    public Integer getUltimoCapituloLido() {
        return ultimoCapituloLido;
    }

    public void setUltimoCapituloLido(Integer ultimoCapituloLido) {
        this.ultimoCapituloLido = ultimoCapituloLido;
    }

    public Integer getQuantidadeExemplares() {
        return quantidadeExemplares;
    }

    public void setQuantidadeExemplares(Integer quantidadeExemplares) {
        this.quantidadeExemplares = quantidadeExemplares;
    }

    public Integer getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(Integer observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public String toString() {
        return "Volume " + numero;
    }
}
