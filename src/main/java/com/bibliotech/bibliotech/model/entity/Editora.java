package com.bibliotech.bibliotech.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_editora")
public class Editora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEditora;

    @Column(nullable = false, length = 100)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "editora", cascade = CascadeType.ALL)
    private List<Exemplar> exemplares = new ArrayList<>();

    public Editora(String nome) {
        this.nome = nome;
    }

    public Editora() {
    }

    public Long getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(Long idEditora) {
        this.idEditora = idEditora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Editora editora = (Editora) o;
        return Objects.equals(idEditora, editora.idEditora);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idEditora);
    }
}
