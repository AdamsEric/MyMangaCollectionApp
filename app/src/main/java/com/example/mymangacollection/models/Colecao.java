package com.example.mymangacollection.models;

import java.io.Serializable;

public class Colecao implements Serializable {
    private String id;
    private String titulo;
    private String tituloOriginal;
    private String tituloAlternativo;
    private String autor;
    private String roteirista;
    private String ilustrador;
    private String editoraId;
    private String serieId;
    private String generoId;
    private String periodicidadeId;
    private String classificacaoIndicativaId;
    private String demografiaId;
    private Integer quantidadeTotalVolumes;
    private boolean stPublicacaoEmAndamento;
    private boolean stPublicacaoOriginalEmAndamento;

    public Colecao() {
        this.id = null;
        this.titulo = null;
        this.tituloOriginal = null;
        this.tituloAlternativo = null;
        this.autor = null;
        this.roteirista = null;
        this.ilustrador = null;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (!titulo.isEmpty()) {
            this.titulo = titulo;
        }
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        if (!tituloOriginal.isEmpty()) {
            this.tituloOriginal = tituloOriginal;
        }
    }

    public String getTituloAlternativo() {
        return tituloAlternativo;
    }

    public void setTituloAlternativo(String tituloAlternativo) {
        if (!tituloAlternativo.isEmpty()) {
            this.tituloAlternativo = tituloAlternativo;
        }
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (!autor.isEmpty()) {
            this.autor = autor;
        }
    }

    public String getRoteirista() {
        return roteirista;
    }

    public void setRoteirista(String roteirista) {
        if (!roteirista.isEmpty()) {
            this.roteirista = roteirista;
        }
    }

    public String getIlustrador() {
        return ilustrador;
    }

    public void setIlustrador(String ilustrador) {
        if (!ilustrador.isEmpty()) {
            this.ilustrador = ilustrador;
        }
    }

    public String getEditoraId() {
        return editoraId;
    }

    public void setEditoraId(String editoraId) {
        this.editoraId = editoraId;
    }

    public String getSerieId() {
        return serieId;
    }

    public void setSerieId(String serieId) {
        this.serieId = serieId;
    }

    public String getGeneroId() {
        return generoId;
    }

    public void setGeneroId(String generoId) {
        this.generoId = generoId;
    }

    public String getPeriodicidadeId() {
        return periodicidadeId;
    }

    public void setPeriodicidadeId(String periodicidadeId) {
        this.periodicidadeId = periodicidadeId;
    }

    public String getClassificacaoIndicativaId() {
        return classificacaoIndicativaId;
    }

    public void setClassificacaoIndicativaId(String classificacaoIndicativaId) {
        this.classificacaoIndicativaId = classificacaoIndicativaId;
    }

    public String getDemografiaId() {
        return demografiaId;
    }

    public void setDemografiaId(String demografiaId) {
        this.demografiaId = demografiaId;
    }

    public Integer getQuantidadeTotalVolumes() {
        return quantidadeTotalVolumes;
    }

    public void setQuantidadeTotalVolumes(Integer quantidadeTotalVolumes) {
        this.quantidadeTotalVolumes = quantidadeTotalVolumes;
    }

    public boolean isStPublicacaoEmAndamento() {
        return stPublicacaoEmAndamento;
    }

    public void setStPublicacaoEmAndamento(boolean stPublicacaoEmAndamento) {
        this.stPublicacaoEmAndamento = stPublicacaoEmAndamento;
    }

    public boolean isStPublicacaoOriginalEmAndamento() {
        return stPublicacaoOriginalEmAndamento;
    }

    public void setStPublicacaoOriginalEmAndamento(boolean stPublicacaoOriginalEmAndamento) {
        this.stPublicacaoOriginalEmAndamento = stPublicacaoOriginalEmAndamento;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
