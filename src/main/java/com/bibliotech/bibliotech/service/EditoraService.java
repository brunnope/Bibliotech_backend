package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Editora;
import com.bibliotech.bibliotech.repository.EditoraRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;

    public List<Editora> listarEditoras() {
        return editoraRepository.findAll();
    }

    public Editora obterEditora(Long id) {
        return editoraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Editora salvarEditora(Editora editora) {
        return editoraRepository.save(editora);
    }

    public Editora atualizarEditora(Long id, Editora editora) {
        Editora entity = editoraRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(entity, editora);
        return editoraRepository.save(entity);
    }

    public void excluir(Long id) {
        editoraRepository.deleteById(id);
    }

    private void updateData(Editora entity, Editora obj) {
        entity.setNome(obj.getNome());
    }
}

