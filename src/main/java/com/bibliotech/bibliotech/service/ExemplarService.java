package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.dto.ExemplarDTO;
import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;
import com.bibliotech.bibliotech.mapper.EditoraMapper;
import com.bibliotech.bibliotech.mapper.ExemplarMapper;
import com.bibliotech.bibliotech.mapper.LivroMapper;
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

    @Autowired
    private LivroMapper livroMapper;

    @Autowired
    private EditoraMapper editoraMapper;

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

    public ExemplarDTO ultimoExemplar() {
        Exemplar exemplar = exemplarRepository.ultimoExemplar();
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
        exemplarAtual.setQuantidadeTotal(exemplarDTO.getQuantidadeTotal());
        exemplarAtual.setQuantidadeDisponivel(exemplarDTO.getQuantidadeDisponivel());
        exemplarAtual.setDisponibilidade(exemplarDTO.getDisponibilidade());
        exemplarAtual.setCapaImg(exemplarDTO.getCapaImg());
        exemplarAtual.setContracapaImg(exemplarDTO.getContracapaImg());
        exemplarAtual.setIdioma(exemplarDTO.getIdioma());
        exemplarAtual.setLivro(livroMapper.toEntity(exemplarDTO.getLivro()));
        exemplarAtual.setEditora(editoraMapper.toEntity(exemplarDTO.getEditora()));

        if (exemplarAtual.getQuantidadeDisponivel() <= 0){
            exemplarAtual.setDisponibilidade(DisponibilidadeExemplar.INDISPONIVEL);
        }else {
            exemplarAtual.setDisponibilidade(DisponibilidadeExemplar.DISPONIVEL);
        }

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