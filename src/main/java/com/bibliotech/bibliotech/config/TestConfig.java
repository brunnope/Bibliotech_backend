package com.bibliotech.bibliotech.config;

import com.bibliotech.bibliotech.entity.*;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;
import com.bibliotech.bibliotech.repository.*;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

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

        // Adicionando ROLE ADMINISTRADOR
        Role roleAdmin = new Role();
        roleAdmin.setRole("ADMINISTRADOR");
        roleAdmin = roleRepository.save(roleAdmin); // Salva no banco

        // Criando usuário com ROLE ADMINISTRADOR
        Usuario usuario = new Usuario();
        usuario.setNome("Usuário Admin");
        usuario.setEmail("admin@exemplo.com");
        usuario.setSenha("senha123");
        usuario.setMatricula("12345678");
        usuario.getRoles().add(roleAdmin);
        usuarioRepository.save(usuario);

        System.out.println("Usuário criado: " + usuario.getNome() + " com papel: " + roleAdmin.getRole());
    }

}