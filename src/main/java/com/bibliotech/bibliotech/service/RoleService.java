package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Role;
import com.bibliotech.bibliotech.entity.dto.RoleDTO;
import com.bibliotech.bibliotech.mapper.RoleMapper;
import com.bibliotech.bibliotech.repository.RoleRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    public List<RoleDTO> listarRoles() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toDTOList(roles);
    }

    public RoleDTO obterRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        return roleMapper.toDTO(role);
    }

    public RoleDTO salvarRole(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role = roleRepository.save(role);
        return roleMapper.toDTO(role);
    }

    public RoleDTO atualizarRole(Long id, RoleDTO roleDTO) {
        Role roleAtual = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        roleAtual.setRole(roleDTO.getRole());
        Role roleAtualizada = roleRepository.save(roleAtual);
        return roleMapper.toDTO(roleAtualizada);
    }

    public void excluirRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        roleRepository.delete(role);
    }
}