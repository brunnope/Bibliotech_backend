package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.model.entity.Livro;
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
    public ResponseEntity<List<Livro>> listarLivros() {
        return ResponseEntity.ok().body(livroService.listarLivros());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> obterLivro(@PathVariable Long id) {
        return ResponseEntity.ok().body(livroService.obterLivro(id));
    }

    @PostMapping
    public ResponseEntity<Livro> salvar(@RequestBody Livro livro) {
        livro = livroService.salvarLivro(livro);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(livro.getIdLivro()).toUri();
        return ResponseEntity.created(uri).body(livro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livro) {
        livro = livroService.atualizarLivro(id, livro);
        return ResponseEntity.ok().body(livro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        livroService.excluirLivro(id);
        return ResponseEntity.noContent().build();
    }
}