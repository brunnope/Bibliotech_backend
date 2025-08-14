package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l ORDER BY l.idLivro DESC LIMIT 1")
    Livro ultimoLivro();

    @Query("SELECT DISTINCT  l.categoria FROM Livro l")
    List<String> listarCategorias();

    List<Livro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase
        (String titulo, String autor);

    @Query("""
       SELECT l FROM Livro l
       WHERE LOWER(l.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))
         AND (LOWER(l.titulo) LIKE LOWER(CONCAT('%', :busca, '%'))
              OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :busca, '%')))
       """)
    List<Livro> findByCategoriaAndTituloOrAutor(String categoria, String busca);

    List<Livro> findByCategoriaContainingIgnoreCase (String categoria);
}
