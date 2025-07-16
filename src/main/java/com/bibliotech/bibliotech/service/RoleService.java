package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Role;
import com.bibliotech.bibliotech.repository.RoleRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> listarRoles() {
        return roleRepository.findAll();
    }

    public Role obterRole(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Role salvarRole(Role role) {
        return roleRepository.save(role);
    }

    public Role atualizarRole(Long id, Role roleAtualizada) {
        Role roleExistente = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));;
        roleExistente.setRole(roleAtualizada.getRole());
        return roleRepository.save(roleExistente);
    }

    public void excluirRole(Long id) {
        roleRepository.deleteById(id);
    }
}