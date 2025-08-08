package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    List<Emprestimo> findByUsuarioIdUsuario(Long idUsuario);

}
