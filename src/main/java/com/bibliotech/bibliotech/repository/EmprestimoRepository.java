package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.entity.Livro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByUsuarioIdUsuario(Long idUsuario);
}
