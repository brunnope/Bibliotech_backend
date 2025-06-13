package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
