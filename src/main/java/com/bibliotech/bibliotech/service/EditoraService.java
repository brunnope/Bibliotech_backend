package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Editora;
import com.bibliotech.bibliotech.entity.dto.EditoraDTO;
import com.bibliotech.bibliotech.mapper.EditoraMapper;
import com.bibliotech.bibliotech.repository.EditoraRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private EditoraMapper editoraMapper;

    public List<EditoraDTO> listarEditoras() {
        List<Editora> editoras = editoraRepository.findAll();
        return editoraMapper.toDTOList(editoras);
    }

    public EditoraDTO obterEditora(Long id) {
        Editora editora = editoraRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        return editoraMapper.toDTO(editora);
    }

    public EditoraDTO salvarEditora(EditoraDTO editoraDTO) {
        Editora editora = editoraMapper.toEntity(editoraDTO);
        editora = editoraRepository.save(editora);
        return editoraMapper.toDTO(editora);
    }

    public EditoraDTO atualizarEditora(Long id, EditoraDTO editoraDTO) {
        Editora editoraAtual = editoraRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        editoraAtual.setNome(editoraDTO.getNome());
        Editora editoraAtualizada = editoraRepository.save(editoraAtual);
        return editoraMapper.toDTO(editoraAtualizada);
    }

    public void excluirEditora(Long id) {
        Editora editora = editoraRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        editoraRepository.delete(editora);
    }
}