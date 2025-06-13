package com.bibliotech.bibliotech.model.entity;

import com.bibliotech.bibliotech.model.entity.enums.DisponibilidadeExemplar;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_exemplar")
public class Exemplar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExemplar;

    @Column(nullable = false)
    private Integer numExemplar;

    private Integer anoPublicacao;

    @Enumerated(EnumType.STRING)
    private DisponibilidadeExemplar disponibilidade;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String capaImg;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contracapaImg;

    @Column(length = 50)
    private String idioma;

    @ManyToOne
    @JoinColumn(name = "id_livro", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "id_editora", nullable = false)
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

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exemplar exemplar = (Exemplar) o;
        return Objects.equals(idExemplar, exemplar.idExemplar);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idExemplar);
    }
}
