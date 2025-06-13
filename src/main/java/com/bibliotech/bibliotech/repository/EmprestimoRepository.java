package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.model.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
