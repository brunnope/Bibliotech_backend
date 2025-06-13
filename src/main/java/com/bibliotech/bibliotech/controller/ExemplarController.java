package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.services.ExemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/exemplares")
public class ExemplarController {

    @Autowired
    private ExemplarService exemplarService;

    @GetMapping
    public ResponseEntity<List<Exemplar>> listarExemplares() {
        return ResponseEntity.ok().body(exemplarService.listarExemplares());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exemplar> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(exemplarService.buscarExemplarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Exemplar> salvar(@RequestBody Exemplar exemplar) {
        exemplar = exemplarService.salvarExemplar(exemplar);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(exemplar.getIdExemplar()).toUri();
        return ResponseEntity.created(uri).body(exemplar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exemplar> atualizar(@PathVariable Long id, @RequestBody Exemplar exemplar) {
        exemplar = exemplarService.atualizarExemplar(id, exemplar);
        return ResponseEntity.ok().body(exemplar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        exemplarService.excluirExemplar(id);
        return ResponseEntity.noContent().build();
    }
}