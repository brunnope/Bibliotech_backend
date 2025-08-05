package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.mapper.LivroMapper;
import com.bibliotech.bibliotech.repository.LivroRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LivroMapper livroMapper;

    public List<LivroDTO> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        return livroMapper.toDTOList(livros);
    }

    public LivroDTO obterLivro(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return livroMapper.toDTO(livro);
    }

    public LivroDTO ultimoLivro() {
        Livro livro = livroRepository.ultimoLivro();
        return livroMapper.toDTO(livro);
    }

    public List<String> listarCategorias() {
        return livroRepository.listarCategorias();
    }

    public LivroDTO salvarLivro(LivroDTO livroDTO) {
        Livro livro = livroMapper.toEntity(livroDTO);
        livro = livroRepository.save(livro);
        return livroMapper.toDTO(livro);
    }

    public LivroDTO atualizarLivro(Long id, LivroDTO livroDTO) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        // Atualiza os campos do livro com os dados do DTO
        livroExistente.setTitulo(livroDTO.getTitulo());
        livroExistente.setAutor(livroDTO.getAutor());
        livroExistente.setCategoria(livroDTO.getCategoria());
        livroExistente.setIsbn(livroDTO.getIsbn());
        livroExistente.setDataCadastro(livroDTO.getDataCadastro());

        livroExistente = livroRepository.save(livroExistente);
        return livroMapper.toDTO(livroExistente);
    }

    public void excluirLivro(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        livroRepository.delete(livro);
    }
}