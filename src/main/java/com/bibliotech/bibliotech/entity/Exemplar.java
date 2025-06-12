package com.bibliotech.bibliotech.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExemplar;

    @Column(nullable = false)
    private Integer numExemplar;

    private Integer anoPublicacao;

    @Column(nullable = false, length = 10)
    private String disponibilidade;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String capaImg;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contracapaImg;

    @Column(length = 50)
    private String idioma;

    @ManyToOne
    @JoinColumn(name = "fk_id_livro", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "fk_id_editora", nullable = false)
    private Editora editora;

    @OneToMany(mappedBy = "exemplar", cascade = CascadeType.ALL)
    private List<Emprestimo> emprestimos;

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

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
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

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
