package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.entity.enums.StatusEmprestimo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByUsuarioIdUsuario(Long idUsuario);

    List<Emprestimo> findByUsuarioIdUsuarioAndStatus(Long idUsuario, StatusEmprestimo status);

    List<Emprestimo> findByUsuarioNomeContainingIgnoreCaseOrUsuarioMatriculaContainingIgnoreCase
            (String nome, String matricula);

    List<Emprestimo> findByStatus (StatusEmprestimo status);

    List<Emprestimo> findByStatusAndUsuarioNomeContainingIgnoreCaseOrUsuarioMatriculaContainingIgnoreCase
            (
        StatusEmprestimo status, String nome, String matricula
    );

    List<Emprestimo> findByDataPrevistaDevolucaoBetween(LocalDate inicio, LocalDate fim);

}
