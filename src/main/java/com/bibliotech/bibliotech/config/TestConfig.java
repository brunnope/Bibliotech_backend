package com.bibliotech.bibliotech.config;

import com.bibliotech.bibliotech.controller.EmailController;
import com.bibliotech.bibliotech.entity.*;
import com.bibliotech.bibliotech.entity.dto.IdentificadorDTO;
import com.bibliotech.bibliotech.entity.dto.RoleDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioComSenhaDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioDTO;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;
import com.bibliotech.bibliotech.entity.enums.StatusEmprestimo;
import com.bibliotech.bibliotech.mapper.RoleMapper;
import com.bibliotech.bibliotech.mapper.UsuarioMapper;
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
    private UsuarioService usuarioService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public void run(String... args) throws Exception {

        Editora editora = new Editora("Principis");
        editora = editoraRepository.save(editora);

        Editora editora2 = new Editora("Companhia das Letras");
        editora2 = editoraRepository.save(editora2);

        Livro livro = new Livro("1984", "George Orwell", "Ficção",
                "6555522267", LocalDate.now());

        Exemplar exemplar1 = new Exemplar(
                1,
                2021,
                2,
                1,
                DisponibilidadeExemplar.DISPONIVEL,
                "https://m.media-amazon.com/images/I/61t0bwt1s3L._SL1000_.jpg",
                "https://m.media-amazon.com/images/I/61GBq2Pbo2L._SL1000_.jpg",
                "Português",
                livro,
                editora
        );

        Exemplar exemplar2 = new Exemplar(
                2,
                2009,
                2,
                0,
                DisponibilidadeExemplar.INDISPONIVEL,
                "https://m.media-amazon.com/images/I/819js3EQwbL._SL1500_.jpg",
                "https://m.media-amazon.com/images/I/812YrJzjlIL._SL1500_.jpg",
                "Português",
                livro,
                editora2
        );


        livro.getExemplares().add(exemplar1);
        livro.getExemplares().add(exemplar2);

        livroRepository.save(livro);


        // Adicionando ROLE ADMINISTRADOR e USER
        Role roleAdmin = new Role();
        roleAdmin.setRole("ADMINISTRADOR");
        roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setRole("USER");
        roleRepository.save(roleUser);


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

        Usuario usuarioTeste = usuarioMapper.toEntity(usuarioService.obterUsuario(Long.parseLong("2")));
        Emprestimo emprestimo = new Emprestimo(
                LocalDate.now(),
                LocalDate.now().plusDays(15),
                null,
                StatusEmprestimo.PENDENTE,
                usuarioTeste,
                exemplar1
        );

        emprestimoRepository.save(emprestimo);

    }
}