package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.dto.IdentificadorDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioComSenhaDTO;
import com.bibliotech.bibliotech.service.UsuarioService;
import com.bibliotech.bibliotech.service.notificacao.EmailService;
import com.bibliotech.bibliotech.service.notificacao.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/enviar/senha")
    public ResponseEntity<String> enviarSenha(@RequestBody IdentificadorDTO identificador) {
        UsuarioComSenhaDTO usuario = usuarioService.buscarPorEmailOuMatricula(identificador.getIdentificador());
        if (!(usuario == null)) {
            try {
                Mensagem mensagem = new Mensagem(usuario.getEmail(), "Redefinição de senha - Bibliotech",
                        "Olá, " + usuario.getNome() + "! Sua senha é: " + usuario.getSenha() + ".");
                emailService.enviarEmail(mensagem);
                return ResponseEntity.ok().body("Email enviado com sucesso!");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

        return ResponseEntity.badRequest().body("Usuário não encontrado!");
    }
}