package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Exemplar;

import com.bibliotech.bibliotech.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
    @Query("SELECT e FROM Exemplar e ORDER BY e.idExemplar DESC LIMIT 1")
    Exemplar ultimoExemplar();
}
