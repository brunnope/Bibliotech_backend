package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.repository.UsuarioRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obterUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(entity, usuarioAtualizado);
        return usuarioRepository.save(entity);
    }

    public void excluirUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }


    public Usuario buscarPorEmailOuMatricula(String identificador) {
        return usuarioRepository.findByEmailOrMatricula(identificador);
    }

    private void updateData(Usuario usuario, Usuario obj) {
        usuario.setMatricula(obj.getMatricula());
        usuario.setNome(obj.getNome());
        usuario.setEmail(obj.getEmail());
        usuario.setSenha(obj.getSenha());
    }
}
