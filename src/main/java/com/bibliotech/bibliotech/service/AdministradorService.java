package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Administrador;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.repository.AdministradorRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }

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

    private void updateData(Administrador adm, Administrador obj) {
        adm.setNome(obj.getNome());
        adm.setSenha(obj.getSenha());
        adm.setEmail(obj.getEmail());
    }
}
