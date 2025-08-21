package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioComSenhaDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioDTO;
import com.bibliotech.bibliotech.mapper.UsuarioMapper;
import com.bibliotech.bibliotech.repository.UsuarioRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDTOList(usuarios);
    }

    public List<UsuarioDTO> listarAlunos(String nome, String matricula) {
        List<Usuario> usuarios;

        boolean temNome = nome != null && !nome.isBlank();
        boolean temMatricula = matricula != null && !matricula.isBlank();

        if (temNome && temMatricula) {
            usuarios = usuarioRepository.listarAlunosPorNomeEMatricula(
                nome, matricula
            );
        } else if (temNome) {
            usuarios = usuarioRepository.listarAlunosPorNome(
                nome
            );
        } else if (temMatricula) {
            usuarios = usuarioRepository.listarAlunosPorMatricula(matricula);
        } else {
            usuarios = usuarioRepository.listarAlunos();
        }

        return usuarioMapper.toDTOList(usuarios);
    }

    public List<UsuarioDTO> listarAdmins(String nome) {
        List<Usuario> admins;

        boolean temNome = nome != null && !nome.isBlank();

        if (temNome) {
            admins = usuarioRepository.listarAdminsPorNome(
                    nome
            );
        } else {
            admins = usuarioRepository.listarAdmins();
        }

        return usuarioMapper.toDTOList(admins);
    }


    public UsuarioDTO obterUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO ultimoUsuario() {
        Usuario usuario = usuarioRepository.ultimoUsuario();
        return usuarioMapper.toDTO(usuario);
    }


    public UsuarioDTO salvarUsuario(UsuarioComSenhaDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new DataIntegrityViolationException("Erro! Este email já está em uso.");
        }

        if (usuarioRepository.existsByMatricula(dto.getMatricula())) {
            throw new DataIntegrityViolationException("Erro! Esta matrícula já está em uso.");
        }

        try {
            Usuario usuario = usuarioMapper.toEntity(dto);
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            usuario = usuarioRepository.save(usuario);
            return usuarioMapper.toDTO(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao salvar o usuário.", e);
        }
    }


    public UsuarioDTO atualizarUsuario(Long id, UsuarioComSenhaDTO dto) {
        Usuario entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if (!entity.getEmail().equals(dto.getEmail())) {
            if (usuarioRepository.existsByEmail(dto.getEmail())) {
                throw new DataIntegrityViolationException("Erro! Este e-mail já está em uso.");
            }
        }

        if (!entity.getMatricula().equals(dto.getMatricula())) {
            if (usuarioRepository.existsByMatricula(dto.getMatricula())) {
                throw new DataIntegrityViolationException("Erro! Esta matrícula já está em uso.");
            }
        }

        entity.setMatricula(dto.getMatricula());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());

        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            entity.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        Usuario atualizado = usuarioRepository.save(entity);
        return usuarioMapper.toDTO(atualizado);
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }


    public UsuarioComSenhaDTO buscarPorEmailOuMatricula(String identificador) {
        return usuarioMapper.toEntityDTOComSenha(usuarioRepository.findByEmailOrMatricula(identificador));
    }


    public UsuarioDTO findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new ResourceNotFoundException();
        }
        return usuarioMapper.toDTO(usuario);
    }

    public Boolean existeUsuario() {
        return usuarioRepository.count() > 0;
    }

}