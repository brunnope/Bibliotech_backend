package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.entity.dto.LoginDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioComSenhaDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioDTO;
import com.bibliotech.bibliotech.mapper.UsuarioMapper;
import com.bibliotech.bibliotech.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private  UsuarioService usuarioService;


    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.listarUsuarios();
        return ResponseEntity.ok().body(usuariosDTO);
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<UsuarioDTO>> listarAlunos(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String matricula
    ) {
        List<UsuarioDTO> alunosDTO = usuarioService.listarAlunos(nome, matricula);
        return ResponseEntity.ok().body(alunosDTO);
    }

    @GetMapping("/admins")
    public ResponseEntity<List<UsuarioDTO>> listarAdmins(
        @RequestParam(required = false) String nome
    ) {
        List<UsuarioDTO> alunosDTO = usuarioService.listarAdmins(nome);
        return ResponseEntity.ok().body(alunosDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterUsuario(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuarioService.obterUsuario(id));
    }


    @GetMapping("/ultimo")
    public ResponseEntity<UsuarioDTO> obterUlitmoUsuario() {
        return ResponseEntity.ok().body(usuarioService.ultimoUsuario());
    }


    @PostMapping
    public ResponseEntity<UsuarioDTO> salvar(@RequestBody UsuarioComSenhaDTO usuarioDTO) {
        UsuarioDTO responseDTO = usuarioService.salvarUsuario(usuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getIdUsuario())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioComSenhaDTO usuarioDTO) {
        UsuarioDTO responseDTO = usuarioService.atualizarUsuario(id, usuarioDTO);
        return ResponseEntity.ok().body(responseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/existe")
    public ResponseEntity<Boolean> existeUsuario() {
        return ResponseEntity.ok(usuarioService.existeUsuario());
    }
}