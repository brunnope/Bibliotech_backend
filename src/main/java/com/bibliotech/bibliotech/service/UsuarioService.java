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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioMapper usuarioMapper;
    

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.toDTOList(usuarios);
    }

    public List<UsuarioDTO> listarAlunos() {
        List<Usuario> usuarios = usuarioRepository.listarAlunos();
        return usuarioMapper.toDTOList(usuarios);
    }

    public List<UsuarioDTO> listarAdmins() {
        List<Usuario> usuarios = usuarioRepository.listarAdmins();
        return usuarioMapper.toDTOList(usuarios);
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


    public UsuarioDTO autenticar(String email, String senha) {
        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado!");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha inválida!");
        }

        return usuarioMapper.toDTO(usuario);
    }


    public UsuarioDTO salvarUsuario(UsuarioComSenhaDTO dto) {
        Usuario usuario = usuarioMapper.toEntity(dto);
        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuario);
    }


    public UsuarioDTO atualizarUsuario(Long id, UsuarioComSenhaDTO dto) {
        Usuario entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        entity.setMatricula(dto.getMatricula());
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            entity.setSenha(dto.getSenha());
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


    private void updateData(Usuario usuario, Usuario obj) {
        usuario.setMatricula(obj.getMatricula());
        usuario.setNome(obj.getNome());
        usuario.setEmail(obj.getEmail());
        usuario.setSenha(obj.getSenha());
    }
}