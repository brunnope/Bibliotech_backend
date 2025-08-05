package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l ORDER BY l.idLivro DESC LIMIT 1")
    Livro ultimoLivro();

    @Query("SELECT DISTINCT  l.categoria FROM Livro l")
    List<String> listarCategorias();

}
