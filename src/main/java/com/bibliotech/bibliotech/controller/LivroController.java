package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros() {
        return ResponseEntity.ok().body(livroService.listarLivros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> obterLivro(@PathVariable Long id) {
        return ResponseEntity.ok().body(livroService.obterLivro(id));
    }

    @PostMapping
    public ResponseEntity<LivroDTO> salvar(@RequestBody LivroDTO livroDTO) {
        LivroDTO novoLivro = livroService.salvarLivro(livroDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoLivro.getIdLivro()).toUri();
        return ResponseEntity.created(uri).body(novoLivro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizar(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
        return ResponseEntity.ok().body(livroService.atualizarLivro(id, livroDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        livroService.excluirLivro(id);
        return ResponseEntity.noContent().build();
    }
}