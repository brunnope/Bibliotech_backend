package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.Editora;
import com.bibliotech.bibliotech.service.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/editoras")
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @GetMapping
    public ResponseEntity<List<Editora>> listarEditoras() {
        return ResponseEntity.ok().body(editoraService.listarEditoras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Editora> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(editoraService.obterEditora(id));
    }

    @PostMapping
    public ResponseEntity<Editora> salvar(@RequestBody Editora editora) {
        editora = editoraService.salvarEditora(editora);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(editora.getIdEditora()).toUri();
        return ResponseEntity.created(uri).body(editora);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Editora> atualizar(@PathVariable Long id, @RequestBody Editora editora) {
        editora = editoraService.atualizarEditora(id, editora);
        return ResponseEntity.ok().body(editora);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        editoraService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}