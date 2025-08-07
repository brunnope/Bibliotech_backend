package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.dto.EditoraDTO;
import com.bibliotech.bibliotech.service.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/editoras")
public class EditoraController {

    @Autowired
    private EditoraService editoraService;

    @GetMapping
    public ResponseEntity<List<EditoraDTO>> listarEditoras() {
        return ResponseEntity.ok().body(editoraService.listarEditoras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EditoraDTO> obterEditora(@PathVariable Long id) {
        return ResponseEntity.ok().body(editoraService.obterEditora(id));
    }

    @PostMapping
    public ResponseEntity<EditoraDTO> salvarEditora(@RequestBody EditoraDTO editoraDTO) {
        EditoraDTO novaEditora = editoraService.salvarEditora(editoraDTO);
        URI uri = fromCurrentRequest().path("/{id}").buildAndExpand(novaEditora.getIdEditora()).toUri();
        return ResponseEntity.created(uri).body(novaEditora);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditoraDTO> atualizarEditora(@PathVariable Long id, @RequestBody EditoraDTO editoraDTO) {
        EditoraDTO atualizada = editoraService.atualizarEditora(id, editoraDTO);
        return ResponseEntity.ok().body(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEditora(@PathVariable Long id) {
        editoraService.excluirEditora(id);
        return ResponseEntity.noContent().build();
    }
}