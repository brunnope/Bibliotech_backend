package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.repository.EmprestimoRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo obterEmprestimo(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Emprestimo salvarEmprestimo(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo atualizarEmprestimo(Long id, Emprestimo emprestimoAtualizado) {
        Emprestimo entity = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(entity, emprestimoAtualizado);
        return emprestimoRepository.save(entity);
    }

    public void excluirEmprestimo(Long id) {
        emprestimoRepository.deleteById(id);
    }

    private void updateData(Emprestimo emprestimo, Emprestimo obj) {
        emprestimo.setDataEmprestimo(obj.getDataEmprestimo());
        emprestimo.setDataPrevistaDevolucao(obj.getDataPrevistaDevolucao());
        emprestimo.setDataDevolucao(obj.getDataDevolucao());
        emprestimo.setStatus(obj.getStatus());
        emprestimo.setUsuario(obj.getUsuario());
        emprestimo.setExemplar(obj.getExemplar());
    }

}
