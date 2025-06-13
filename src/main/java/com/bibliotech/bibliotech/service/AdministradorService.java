package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Administrador;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    public Administrador obterAdministrador(Long id) {
        return administradorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Administrador salvarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Administrador atualizarAdministrador(Long id, Administrador administrador) {
        Administrador entity = administradorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(entity, administrador);
        return administradorRepository.save(entity);
    }

    public void excluirAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }

    private void updateData(Administrador entity, Administrador obj) {
        entity.setNome(obj.getNome());
        entity.setSenha(obj.getSenha());
        entity.setEmail(obj.getEmail());
    }
}
