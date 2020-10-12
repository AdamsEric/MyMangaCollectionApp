package com.example.mymangacollection.models;

public class ClassificacaoIndicativa {
    private String id;
    private String sigla;
    private String descricao;
    private String conteudo;
    private String corHexIndicacao;

    public ClassificacaoIndicativa() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getCorHexIndicacao() {
        return corHexIndicacao;
    }

    public void setCorHexIndicacao(String corHexIndicacao) {
        this.corHexIndicacao = corHexIndicacao;
    }

    @Override
    public String toString() {
        return this.sigla;
    }
}
