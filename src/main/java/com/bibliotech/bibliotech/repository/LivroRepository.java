package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.model.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
