package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.service.EmprestimoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarEmprestimos() {
        return ResponseEntity.ok().body(emprestimoService.listarEmprestimos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> obterEmprestimo(@PathVariable Long id) {
        return ResponseEntity.ok().body(emprestimoService.obterEmprestimo(id));
    }

    @PostMapping
    public ResponseEntity<Emprestimo> salvar(@RequestBody Emprestimo emprestimo) {
        emprestimo = emprestimoService.salvarEmprestimo(emprestimo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(emprestimo.getIdEmprestimo())
                .toUri();
        return ResponseEntity.created(uri).body(emprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> atualizarEmprestimo(@PathVariable Long id, @RequestBody Emprestimo emprestimoAtualizado) {
        Emprestimo emprestimo = emprestimoService.atualizarEmprestimo(id, emprestimoAtualizado);
        return ResponseEntity.ok().body(emprestimo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        emprestimoService.excluirEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}
