package com.bibliotech.bibliotech.config;

import com.bibliotech.bibliotech.entity.Editora;
import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;
import com.bibliotech.bibliotech.repository.EditoraRepository;
import com.bibliotech.bibliotech.repository.ExemplarRepository;
import com.bibliotech.bibliotech.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
    
    @Autowired
    private LivroRepository livroRepository;
    
    @Autowired
    private EditoraRepository editoraRepository;
    
    @Autowired        
    private ExemplarRepository exemplarRepository;    
            
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

    }
}