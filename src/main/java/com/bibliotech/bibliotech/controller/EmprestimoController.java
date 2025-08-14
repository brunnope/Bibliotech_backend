package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.entity.dto.EmprestimoDTO;
import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.entity.enums.StatusEmprestimo;
import com.bibliotech.bibliotech.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping
    public ResponseEntity<List<EmprestimoDTO>> listarEmprestimos(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String busca) {
        List<EmprestimoDTO> emprestimos = emprestimoService.listarEmprestimos(status, busca);
        return ResponseEntity.ok().body(emprestimos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> obterEmprestimo(@PathVariable Long id) {
        EmprestimoDTO emprestimo = emprestimoService.obterEmprestimo(id);
        return ResponseEntity.ok().body(emprestimo);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<EmprestimoDTO>> buscarPorUsuario(
        @RequestParam(required = false) String status,
        @PathVariable Long idUsuario) {
        List<EmprestimoDTO> emprestimos = emprestimoService.buscarPorUsuario(status, idUsuario);
        return ResponseEntity.ok(emprestimos);
    }

    @PostMapping
    public ResponseEntity<EmprestimoDTO> salvarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO) {
        EmprestimoDTO novoEmprestimo = emprestimoService.salvarEmprestimo(emprestimoDTO);
        URI uri = fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoEmprestimo.getIdEmprestimo()).toUri();
        return ResponseEntity.created(uri).body(novoEmprestimo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> atualizarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDTO emprestimoDTO) {
        EmprestimoDTO atualizado = emprestimoService.atualizarEmprestimo(id, emprestimoDTO);
        return ResponseEntity.ok().body(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEmprestimo(@PathVariable Long id) {
        emprestimoService.excluirEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}