package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.entity.dto.EmprestimoDTO;
import com.bibliotech.bibliotech.entity.enums.StatusEmprestimo;
import com.bibliotech.bibliotech.mapper.EmprestimoMapper;
import com.bibliotech.bibliotech.repository.EmprestimoRepository;
import com.bibliotech.bibliotech.repository.ExemplarRepository;
import com.bibliotech.bibliotech.repository.UsuarioRepository;
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

    @Autowired
    private ExemplarRepository exemplarRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

        Exemplar exemplar = exemplarRepository.findById(emprestimo.getExemplar().getIdExemplar())
                .orElseThrow(() -> new ResourceNotFoundException(emprestimoDTO.getExemplar().getIdExemplar()));

        if (exemplar.getQuantidadeDisponivel() <= 0) {
            throw new RuntimeException("Exemplar indisponível para empréstimo");
        }

        exemplar.setQuantidadeDisponivel(exemplar.getQuantidadeDisponivel() - 1);
        exemplarRepository.save(exemplar);

        emprestimo = emprestimoRepository.save(emprestimo);
        return emprestimoMapper.toDTO(emprestimo);
    }

    public EmprestimoDTO atualizarEmprestimo(Long id, EmprestimoDTO emprestimoAtualizado) {
        Emprestimo emprestimoAtual = emprestimoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        Exemplar exemplarAntigo = emprestimoAtual.getExemplar();
        StatusEmprestimo statusAntigo = emprestimoAtual.getStatus();

        updateData(emprestimoAtual, emprestimoAtualizado);

        Exemplar exemplarNovo = exemplarRepository.findById(emprestimoAtual.getExemplar().getIdExemplar())
                .orElseThrow(() -> new ResourceNotFoundException("Exemplar não encontrado"));

        if (!exemplarAntigo.getIdExemplar().equals(exemplarNovo.getIdExemplar())) {

            exemplarAntigo.setQuantidadeDisponivel(exemplarAntigo.getQuantidadeDisponivel() + 1);
            exemplarRepository.save(exemplarAntigo);


            if (exemplarNovo.getQuantidadeDisponivel() <= 0) {
                throw new RuntimeException("Novo exemplar está indisponível");
            }
            exemplarNovo.setQuantidadeDisponivel(exemplarNovo.getQuantidadeDisponivel() - 1);
            exemplarRepository.save(exemplarNovo);
        }

        if (statusAntigo == StatusEmprestimo.PENDENTE &&
                emprestimoAtual.getStatus() == StatusEmprestimo.DEVOLVIDO) {

            exemplarNovo.setQuantidadeDisponivel(exemplarNovo.getQuantidadeDisponivel() + 1);
            exemplarRepository.save(exemplarNovo);
        }

        emprestimoAtual = emprestimoRepository.save(emprestimoAtual);
        return emprestimoMapper.toDTO(emprestimoAtual);
    }


    public void excluirEmprestimo(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        emprestimoRepository.delete(emprestimo);
    }


    private void updateData(Emprestimo atual, EmprestimoDTO atualizado) {
        atual.setDataEmprestimo(atualizado.getDataEmprestimo());
        atual.setDataPrevistaDevolucao(atualizado.getDataPrevistaDevolucao());
        atual.setDataDevolucao(atualizado.getDataDevolucao());
        atual.setStatus(atualizado.getStatus());

        Exemplar novoExemplar = exemplarRepository.findById(atualizado.getExemplar().getIdExemplar())
                .orElseThrow(() -> new ResourceNotFoundException(atualizado.getExemplar().getIdExemplar()));
        atual.setExemplar(novoExemplar);

        Usuario usuario = usuarioRepository.findById(atualizado.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException(atualizado.getUsuario().getIdUsuario()));
        atual.setUsuario(usuario);
    }


}
