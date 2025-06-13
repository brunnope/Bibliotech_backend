package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.model.entity.Exemplar;
import com.bibliotech.bibliotech.repository.ExemplarRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExemplarService {

    @Autowired
    private ExemplarRepository exemplarRepository;

    public List<Exemplar> listarExemplares() {
        return exemplarRepository.findAll();
    }

    public Exemplar obterExemplar(Long id) {
        return exemplarRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Exemplar salvarExemplar(Exemplar exemplar) {
        return exemplarRepository.save(exemplar);
    }

    public Exemplar atualizarExemplar(Long id, Exemplar exemplar) {
        Exemplar entity = exemplarRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(entity, exemplar);
        return exemplarRepository.save(entity);
    }

    public void excluirExemplar(Long id) {
        exemplarRepository.deleteById(id);
    }

    private void updateData(Exemplar entity, Exemplar obj) {
        entity.setNumExemplar(obj.getNumExemplar());
        entity.setAnoPublicacao(obj.getAnoPublicacao());
        entity.setDisponibilidade(obj.getDisponibilidade());
        entity.setCapaImg(obj.getCapaImg());
        entity.setContracapaImg(obj.getContracapaImg());
        entity.setIdioma(obj.getIdioma());
        entity.setLivro(obj.getLivro());
        entity.setEditora(obj.getEditora());
    }
}