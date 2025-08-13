package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.email = :identificador OR u.matricula = :identificador")
    Usuario findByEmailOrMatricula(String identificador);

    boolean existsByEmail(String email);

    boolean existsByMatricula(String matricula);

    Usuario findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE u.role.role = 'USER'")
    List<Usuario> listarAlunos();

    @Query("SELECT u FROM Usuario u WHERE u.role.role = 'ADMINISTRADOR'")
    List<Usuario> listarAdmins();

    @Query("SELECT u FROM Usuario u ORDER BY u.idUsuario DESC LIMIT 1")
    Usuario ultimoUsuario();
}
