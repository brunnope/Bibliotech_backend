package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.dto.ExemplarDTO;
import com.bibliotech.bibliotech.mapper.ExemplarMapper;
import com.bibliotech.bibliotech.repository.ExemplarRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExemplarService {

    @Autowired
    private ExemplarRepository exemplarRepository;

    @Autowired
    private ExemplarMapper exemplarMapper;

    public List<ExemplarDTO> listarExemplares() {
        List<Exemplar> exemplares = exemplarRepository.findAll();
        return exemplarMapper.toDTOList(exemplares);
    }

    public ExemplarDTO obterExemplar(Long id) {
        Exemplar exemplar = exemplarRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        return exemplarMapper.toDTO(exemplar);
    }

    public ExemplarDTO salvarExemplar(ExemplarDTO exemplarDTO) {
        Exemplar exemplar = exemplarMapper.toEntity(exemplarDTO);
        exemplar = exemplarRepository.save(exemplar);
        return exemplarMapper.toDTO(exemplar);
    }

    public ExemplarDTO atualizarExemplar(Long id, ExemplarDTO exemplarDTO) {
        Exemplar exemplarAtual = exemplarRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );

        exemplarAtual.setNumExemplar(exemplarDTO.getNumExemplar());
        exemplarAtual.setAnoPublicacao(exemplarDTO.getAnoPublicacao());
        exemplarAtual.setDisponibilidade(exemplarDTO.getDisponibilidade());
        exemplarAtual.setCapaImg(exemplarDTO.getCapaImg());
        exemplarAtual.setContracapaImg(exemplarDTO.getContracapaImg());
        exemplarAtual.setIdioma(exemplarDTO.getIdioma());
        exemplarAtual.setLivro(exemplarDTO.getLivro());
        exemplarAtual.setEditora(exemplarDTO.getEditora());

        Exemplar exemplarAtualizado = exemplarRepository.save(exemplarAtual);
        return exemplarMapper.toDTO(exemplarAtualizado);
    }

    public void excluirExemplar(Long id) {
        Exemplar exemplar = exemplarRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        exemplarRepository.delete(exemplar);
    }


}