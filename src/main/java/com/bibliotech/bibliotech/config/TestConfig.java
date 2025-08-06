package com.bibliotech.bibliotech.config;

import com.bibliotech.bibliotech.controller.EmailController;
import com.bibliotech.bibliotech.entity.*;
import com.bibliotech.bibliotech.entity.dto.IdentificadorDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioComSenhaDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioDTO;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;
import com.bibliotech.bibliotech.repository.*;
import com.bibliotech.bibliotech.service.UsuarioService;
import com.bibliotech.bibliotech.service.notificacao.EmailService;
import com.bibliotech.bibliotech.service.notificacao.Mensagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private EditoraRepository editoraRepository;
    
    @Autowired        
    private ExemplarRepository exemplarRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailController emailController;

    @Override
    public void run(String... args) throws Exception {



        Editora editora = new Editora("Editora Exemplo");
        editora = editoraRepository.save(editora);

        Livro livro = new Livro("Livro Exemplo", "Autor Exemplo", "Ficção",
                "1234567890123", LocalDate.now());

        Exemplar exemplar1 = new Exemplar(
                1,                       // numExemplar
                2023,                    // anoPublicacao
                DisponibilidadeExemplar.DISPONIVEL, // disponibilidade
                "URL_CAPA_1",            // capaImg
                "URL_CONTRACAPA_1",      // contracapaImg
                "Português",             // idioma
                livro,                   // livro
                editora                  // editora
        );

        Exemplar exemplar2 = new Exemplar(
                2,
                2022,
                DisponibilidadeExemplar.INDISPONIVEL,
                "URL_CAPA_2",
                "URL_CONTRACAPA_2",
                "Inglês",
                livro,
                editora
        );


        livro.getExemplares().add(exemplar1);
        livro.getExemplares().add(exemplar2);

        //Salva o Livro e os Exemplares (cascade no Livro persiste os Exemplares)
        livroRepository.save(livro);

        // Adicionando ROLE ADMINISTRADOR e USER
        Role roleAdmin = new Role();
        roleAdmin.setRole("ADMINISTRADOR");
        roleAdmin = roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setRole("USER");
        roleUser = roleRepository.save(roleUser);


        // Criando usuário com ROLE ADMINISTRADOR
        UsuarioComSenhaDTO usuario = new UsuarioComSenhaDTO();
        usuario.setNome("Usuário Admin");
        usuario.setEmail("cicero.brunno@academico.ifpb.edu.br");
        usuario.setSenha("senha123");
        usuario.setMatricula("12345678");
        usuario.setRole(roleAdmin);
        usuarioService.salvarUsuario(usuario);

        // Criando usuário com ROLE USER
        UsuarioComSenhaDTO usuario2 = new UsuarioComSenhaDTO();
        usuario2.setNome("Usuário Aluno");
        usuario2.setEmail("cicerobrnn111@gmail.com");
        usuario2.setSenha("senha123");
        usuario2.setMatricula("202315020028");
        usuario2.setRole(roleUser);
        usuarioService.salvarUsuario(usuario2);

    }
}