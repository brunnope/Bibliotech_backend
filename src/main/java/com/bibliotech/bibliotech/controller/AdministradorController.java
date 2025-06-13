package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.model.entity.Administrador;
import com.bibliotech.bibliotech.service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorService administradorService;
    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdiministradores() {
        return ResponseEntity.ok().body(administradorService.listarAdministradores());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Administrador> obterAdministrador(@PathVariable Long id) {
        return ResponseEntity.ok().body(administradorService.obterAdministrador(id));
    }

    @PostMapping
    public ResponseEntity<Administrador> salvar(@RequestBody Administrador administrador) {
        administrador = administradorService.salvarAdministrador(administrador);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(administrador.getIdAdmin()).toUri();
        return ResponseEntity.created(uri).body(administrador);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizarAdministrador(@PathVariable Long id, @RequestBody Administrador administradorAtualizado) {
        Administrador administrador = administradorService.atualizarAdministrador(id, administradorAtualizado);
        return ResponseEntity.ok().body(administrador);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        administradorService.excluirAdministrador(id);
        return ResponseEntity.noContent().build();
    }


}
