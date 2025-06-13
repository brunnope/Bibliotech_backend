package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.model.entity.Livro;
import com.bibliotech.bibliotech.repository.LivroRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro obterLivro(Long id) {
        return livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Livro salvarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Livro atualizarLivro(Long id, Livro livro) {
        Livro entity = livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(entity, livro);
        return livroRepository.save(entity);
    }

    public void excluirLivro(Long id) {
        livroRepository.deleteById(id);
    }

    private void updateData(Livro entity, Livro obj) {
        entity.setTitulo(obj.getTitulo());
        entity.setAutor(obj.getAutor());
        entity.setCategoria(obj.getCategoria());
        entity.setIsbn(obj.getIsbn());
        entity.setDataCadastro(obj.getDataCadastro());
    }
}