package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.dto.ExemplarDTO;
import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.service.ExemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = "/exemplares")
public class ExemplarController {

    @Autowired
    private ExemplarService exemplarService;

    @GetMapping
    public ResponseEntity<List<ExemplarDTO>> listarExemplares(
        @RequestParam(required = false) String disponibilidade,
        @RequestParam(required = false) String titulo) {
        return ResponseEntity.ok().body(exemplarService.listarExemplares(disponibilidade, titulo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExemplarDTO> obterExemplar(@PathVariable Long id) {
        return ResponseEntity.ok().body(exemplarService.obterExemplar(id));
    }

    @GetMapping("/ultimo")
    public ResponseEntity<ExemplarDTO> obterUltimoExemplar() {
        return ResponseEntity.ok().body(exemplarService.ultimoExemplar());
    }

    @PostMapping
    public ResponseEntity<ExemplarDTO> salvar(@RequestBody ExemplarDTO exemplarDTO) {
        ExemplarDTO novoExemplar = exemplarService.salvarExemplar(exemplarDTO);
        URI uri = fromCurrentRequest().path("/{id}")
                .buildAndExpand(novoExemplar.getIdExemplar()).toUri();
        return ResponseEntity.created(uri).body(novoExemplar);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExemplarDTO> atualizar(@PathVariable Long id, @RequestBody ExemplarDTO exemplarDTO) {
        return ResponseEntity.ok().body(exemplarService.atualizarExemplar(id, exemplarDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        exemplarService.excluirExemplar(id);
        return ResponseEntity.noContent().build();
    }
}