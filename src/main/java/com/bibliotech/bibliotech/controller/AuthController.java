package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.entity.dto.LoginDTO;
import com.bibliotech.bibliotech.entity.dto.TokenDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioDTO;
import com.bibliotech.bibliotech.service.UsuarioService;
import com.bibliotech.bibliotech.service.autenticacao.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = null;
        try {

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtService.generateToken(authentication);

            UsuarioDTO userdto = usuarioService.findByEmail(loginDTO.getEmail());

            TokenDTO tokendto = new TokenDTO(token, userdto);

            return ResponseEntity.ok(tokendto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout bem-sucedido.");
    }
}
