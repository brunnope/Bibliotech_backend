package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
