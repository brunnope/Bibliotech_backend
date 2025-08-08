package com.bibliotech.bibliotech.entity.dto;

import com.bibliotech.bibliotech.entity.Editora;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;

public class ExemplarDTO {
    private Long idExemplar;
    private Integer numExemplar;
    private Integer anoPublicacao;
    private Integer quantidadeTotal;
    private Integer quantidadeDisponivel;
    private DisponibilidadeExemplar disponibilidade;
    private String capaImg;
    private String contracapaImg;
    private String idioma;
    private LivroDTO livro;
    private EditoraDTO editora;


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

    public Integer getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public void setQuantidadeTotal(Integer quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
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

    public LivroDTO getLivro() {
        return livro;
    }

    public void setLivro(LivroDTO livro) {
        this.livro = livro;
    }

    public EditoraDTO getEditora() {
        return editora;
    }

    public void setEditora(EditoraDTO editora) {
        this.editora = editora;
    }
}