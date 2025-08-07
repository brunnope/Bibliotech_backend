package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.entity.dto.EmprestimoDTO;
import com.bibliotech.bibliotech.mapper.EmprestimoMapper;
import com.bibliotech.bibliotech.repository.EmprestimoRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private EmprestimoMapper emprestimoMapper;

    public List<EmprestimoDTO> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        return emprestimoMapper.toDTOList(emprestimos);
    }

    public EmprestimoDTO obterEmprestimo(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        return emprestimoMapper.toDTO(emprestimo);
    }

    public EmprestimoDTO salvarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Emprestimo emprestimo = emprestimoMapper.toEntity(emprestimoDTO);
        emprestimo = emprestimoRepository.save(emprestimo);
        return emprestimoMapper.toDTO(emprestimo);
    }


    public EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO emprestimoAtualizado) {
        Emprestimo emprestimoAtual
                = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        updateData(emprestimoAtual, emprestimoAtualizado);
        emprestimoAtual = emprestimoRepository.save(emprestimoAtual);
        return emprestimoMapper.toDTO(emprestimoAtual);

    }

    public void excluirEmprestimo(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        emprestimoRepository.delete(emprestimo);
    }


    private void updateData(Emprestimo emprestimo, EmprestimoDTO obj) {
        emprestimo.setDataEmprestimo(obj.getDataEmprestimo());
        emprestimo.setDataPrevistaDevolucao(obj.getDataPrevistaDevolucao());
        emprestimo.setDataDevolucao(obj.getDataDevolucao());
        emprestimo.setUsuario(obj.getUsuario());
        emprestimo.setExemplar(obj.getExemplar());
    }

}
