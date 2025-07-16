package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.Role;
import com.bibliotech.bibliotech.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> listarRoles() {
        return ResponseEntity.ok().body(roleService.listarRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> buscarRole(@PathVariable Long id) {
        return ResponseEntity.ok().body(roleService.obterRole(id));
    }

    @PostMapping
    public ResponseEntity<Role> salvar(@RequestBody Role role) {
        role = roleService.salvarRole(role);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(role.getId()).toUri();
        return ResponseEntity.created(uri).body(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> atualizar(@PathVariable Long id, @RequestBody Role role) {
        role = roleService.atualizarRole(id, role);
        return ResponseEntity.ok().body(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        roleService.excluirRole(id);
        return ResponseEntity.noContent().build();
    }
}