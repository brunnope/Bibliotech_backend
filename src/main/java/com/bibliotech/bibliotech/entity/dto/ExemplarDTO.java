package com.bibliotech.bibliotech.entity.dto;

import com.bibliotech.bibliotech.entity.Editora;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;

public class ExemplarDTO {
    private Long idExemplar;
    private Integer numExemplar;
    private Integer anoPublicacao;
    private DisponibilidadeExemplar disponibilidade;
    private String capaImg;
    private String contracapaImg;
    private String idioma;
    private Livro livro;
    private Editora editora;


    public Long getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(Long idExemplar) {
        this.idExemplar = idExemplar;
    }

    public Integer getNumExemplar() {
        return numExemplar;
    }

    public void setNumExemplar(Integer numExemplar) {
        this.numExemplar = numExemplar;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public DisponibilidadeExemplar getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(DisponibilidadeExemplar disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getCapaImg() {
        return capaImg;
    }

    public void setCapaImg(String capaImg) {
        this.capaImg = capaImg;
    }

    public String getContracapaImg() {
        return contracapaImg;
    }

    public void setContracapaImg(String contracapaImg) {
        this.contracapaImg = contracapaImg;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }
}