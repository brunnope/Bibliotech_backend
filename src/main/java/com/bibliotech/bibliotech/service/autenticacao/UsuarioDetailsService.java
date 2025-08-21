package com.bibliotech.bibliotech.service.autenticacao;

import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            throw  new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email);
        }

        return usuario;
    }
}
