package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.dto.RoleDTO;
import com.bibliotech.bibliotech.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDTO>> listarRoles() {
        return ResponseEntity.ok().body(roleService.listarRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> obterRole(@PathVariable Long id) {
        return ResponseEntity.ok().body(roleService.obterRole(id));
    }

    @PostMapping
    public ResponseEntity<RoleDTO> salvarRole(@RequestBody RoleDTO roleDTO) {
        RoleDTO novaRole = roleService.salvarRole(roleDTO);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(novaRole.getId()).toUri();
        return ResponseEntity.created(uri).body(novaRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> atualizarRole(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        RoleDTO atualizada = roleService.atualizarRole(id, roleDTO);
        return ResponseEntity.ok().body(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirRole(@PathVariable Long id) {
        roleService.excluirRole(id);
        return ResponseEntity.noContent().build();
    }
}