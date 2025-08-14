package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Exemplar;

import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
    @Query("SELECT e FROM Exemplar e ORDER BY e.idExemplar DESC LIMIT 1")
    Exemplar ultimoExemplar();

    List<Exemplar> findByDisponibilidade(DisponibilidadeExemplar disponibilidade);

    List<Exemplar> findByLivroTituloContainingIgnoreCase(String titulo);

    List<Exemplar> findByDisponibilidadeAndLivroTituloContainingIgnoreCase(
            DisponibilidadeExemplar disponibilidade, String titulo
    );

}
