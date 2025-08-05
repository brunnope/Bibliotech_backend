package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :identificador OR u.matricula = :identificador")
    Usuario findByEmailOrMatricula(String identificador);


}
