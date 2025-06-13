package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.model.entity.Administrador;
import com.bibliotech.bibliotech.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obterAdministrador(@PathVariable Long id) {
        return administradorService.buscarAdministradorPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Administrador adicionarAdministrador(@RequestBody Administrador administrador) {
        return administradorService.salvarAdministrador(administrador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerAdministradorPorId(@PathVariable Long id) {
        administradorService.excluirAdministradorPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizarAdministrador(@PathVariable Long id, @RequestBody Administrador administradorAtualizado) {
        return administradorService.buscarAdministradorPorId(id)
                .map(administradorExistente -> {
                    administradorExistente.setNome(administradorAtualizado.getNome());
                    administradorExistente.setEmail(administradorAtualizado.getEmail());
                    administradorExistente.setSenha(administradorAtualizado.getSenha());

                    Administrador atualizado = administradorService.salvarAdministrador(administradorExistente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
