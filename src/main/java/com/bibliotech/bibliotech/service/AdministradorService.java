package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.model.entity.Administrador;
import com.bibliotech.bibliotech.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public Administrador salvarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Optional<Administrador> buscarAdministradorPorId(Long id) {
        return administradorRepository.findById(id);
    }

    public List<Administrador> buscarAdministradores() {
        return administradorRepository.findAll();
    }

    public void excluirAdministrador(Administrador administrador) {
        administradorRepository.delete(administrador);
    }

    public void excluirAdministradorPorId(Long id) {
        administradorRepository.deleteById(id);
    }
}
