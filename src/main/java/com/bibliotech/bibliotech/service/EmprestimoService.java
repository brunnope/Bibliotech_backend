package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.Livro;
import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.entity.dto.EmprestimoDTO;
import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.entity.enums.DisponibilidadeExemplar;
import com.bibliotech.bibliotech.entity.enums.StatusEmprestimo;
import com.bibliotech.bibliotech.mapper.EmprestimoMapper;
import com.bibliotech.bibliotech.repository.EmprestimoRepository;
import com.bibliotech.bibliotech.repository.ExemplarRepository;
import com.bibliotech.bibliotech.repository.UsuarioRepository;
import com.bibliotech.bibliotech.service.exceptions.ResourceNotFoundException;
import com.bibliotech.bibliotech.service.notificacao.EmailService;
import com.bibliotech.bibliotech.service.notificacao.Mensagem;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.ObjectInputFilter.Status;
import java.time.LocalDate;
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
    
    @Autowired
    private EmailService emailService;

    public List<EmprestimoDTO> listarEmprestimos(String status, String busca) {
        List<Emprestimo> emprestimos;

        boolean temStatus = status != null && !status.isBlank();

        StatusEmprestimo statusEmprestimo = null;

        if (temStatus) {
            statusEmprestimo = StatusEmprestimo.valueOf(status);
        }

        boolean temBusca = busca != null && !busca.isBlank();


        if (temStatus && temBusca) {
            emprestimos = emprestimoRepository.findByStatusAndUsuarioNomeContainingIgnoreCaseOrUsuarioMatriculaContainingIgnoreCase(
                statusEmprestimo, busca, busca
            );
        } else if (temBusca) {
            emprestimos = emprestimoRepository.findByUsuarioNomeContainingIgnoreCaseOrUsuarioMatriculaContainingIgnoreCase(
                busca, busca
            );
        } else if (temStatus) {
            emprestimos = emprestimoRepository.findByStatus(statusEmprestimo);
        } else {
            emprestimos = emprestimoRepository.findAll();
        }

        return emprestimoMapper.toDTOList(emprestimos);
    }

    public EmprestimoDTO obterEmprestimo(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        return emprestimoMapper.toDTO(emprestimo);
    }

    public List<EmprestimoDTO> buscarPorUsuario(String status,Long idUsuario) {
        List<Emprestimo> emprestimos;

        boolean temStatus = status != null && !status.isBlank();

        StatusEmprestimo statusEmprestimo = null;

        if (temStatus) {
            statusEmprestimo = StatusEmprestimo.valueOf(status);
        }

        if (temStatus) {
            emprestimos = emprestimoRepository.findByUsuarioIdUsuarioAndStatus(idUsuario, statusEmprestimo);
        } else {
            emprestimos = emprestimoRepository.findByUsuarioIdUsuario(idUsuario);
        }

        return emprestimoMapper.toDTOList(emprestimos);
    }

    public EmprestimoDTO salvarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Emprestimo emprestimo = emprestimoMapper.toEntity(emprestimoDTO);

        Exemplar exemplar = exemplarRepository.findById(emprestimo.getExemplar().getIdExemplar())
                .orElseThrow(() -> new ResourceNotFoundException(emprestimoDTO.getExemplar().getIdExemplar()));

        if (exemplar.getQuantidadeDisponivel() <= 0) {
            throw new RuntimeException("Exemplar indisponível para empréstimo");
        }

        exemplar.setQuantidadeDisponivel(exemplar.getQuantidadeDisponivel() - 1);

        if (exemplar.getQuantidadeDisponivel() <= 0){
            exemplar.setDisponibilidade(DisponibilidadeExemplar.INDISPONIVEL);
        }

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

    StatusEmprestimo statusNovo = emprestimoAtual.getStatus();

 
    if (!exemplarAntigo.getIdExemplar().equals(exemplarNovo.getIdExemplar())) {

        if (exemplarNovo.getQuantidadeDisponivel() <= 0) {
            throw new RuntimeException("Novo exemplar está indisponível");
        }

 
        exemplarAntigo.setQuantidadeDisponivel(exemplarAntigo.getQuantidadeDisponivel() + 1);
        exemplarAntigo.setDisponibilidade(DisponibilidadeExemplar.DISPONIVEL);

        if (exemplarAntigo.getQuantidadeDisponivel() > exemplarAntigo.getQuantidadeTotal()) {
            exemplarAntigo.setQuantidadeDisponivel(exemplarAntigo.getQuantidadeTotal());
        }
        exemplarRepository.save(exemplarAntigo);

        
        aplicarAtualizacaoDeQuantidade(statusAntigo, statusNovo, exemplarNovo);

        if (exemplarNovo.getQuantidadeDisponivel() <= 0) {
            exemplarNovo.setDisponibilidade(DisponibilidadeExemplar.INDISPONIVEL);
        }
        exemplarRepository.save(exemplarNovo);

    } else {
        
        aplicarAtualizacaoDeQuantidade(statusAntigo, statusNovo, exemplarNovo);

        if (exemplarNovo.getQuantidadeDisponivel() <= 0) {
            exemplarNovo.setDisponibilidade(DisponibilidadeExemplar.INDISPONIVEL);
        } else {
            exemplarNovo.setDisponibilidade(DisponibilidadeExemplar.DISPONIVEL);
        }
        exemplarRepository.save(exemplarNovo);
    }

    emprestimoAtual = emprestimoRepository.save(emprestimoAtual);
    return emprestimoMapper.toDTO(emprestimoAtual);
}



    public void excluirEmprestimo(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(id));

        Exemplar exemplar = emprestimo.getExemplar();
        exemplar.setQuantidadeDisponivel(exemplar.getQuantidadeDisponivel() + 1);
        exemplar.setDisponibilidade(DisponibilidadeExemplar.DISPONIVEL);
        exemplarRepository.save(exemplar);

        emprestimoRepository.delete(emprestimo);
    }

    public void notificarUsuarios() {
        LocalDate hoje = LocalDate.now();
        LocalDate limiteProximoAVencimento = hoje.plusDays(3);

        List<Emprestimo> proximosDeVencer = emprestimoRepository.findByStatus(StatusEmprestimo.PENDENTE);

        List<Emprestimo> atrasados = emprestimoRepository.findByStatus(StatusEmprestimo.ATRASADO);

        for (Emprestimo emprestimo : proximosDeVencer) {
            if (emprestimo.getDataPrevistaDevolucao() != null &&
                emprestimo.getDataPrevistaDevolucao().isEqual(limiteProximoAVencimento)) {

                enviarNotificacao(emprestimo,
                    "Sua devolução está próxima!",
                    "Prezado " + emprestimo.getUsuario().getNome() +
                    ", o prazo para devolução do seu empréstimo está próximo. Restam apenas 3 dias."
                );
            }
        }

        for (Emprestimo emprestimo : atrasados) {
            enviarNotificacao(emprestimo,
                "Seu empréstimo está atrasado!",
                "Prezado " + emprestimo.getUsuario().getNome() +
                ", o prazo para devolução do livro " + emprestimo.getExemplar().getLivro().getTitulo() + " já expirou." +
                " Por favor, regularize a devolução o mais rápido possível para evitar sanções."
            );
        }
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void executarNotificacoesAgendadas() {
        notificarUsuarios();
    }


    private void enviarNotificacao(Emprestimo emprestimo, String titulo, String conteudo) {
        try {
            emailService.enviarEmail(new Mensagem(emprestimo.getUsuario().getEmail(), titulo, conteudo));
        } catch (EmailException e) {
            System.err.println("Erro ao enviar e-mail para " + emprestimo.getUsuario().getEmail() + ": " + e.getMessage());
        }
    }


    private void aplicarAtualizacaoDeQuantidade(StatusEmprestimo statusAntigo, StatusEmprestimo statusNovo, 
                Exemplar exemplarNovo){

        if (!statusAntigo.equals(statusNovo)) {

            if (statusAntigo == StatusEmprestimo.PENDENTE && statusNovo == StatusEmprestimo.DEVOLVIDO) {
                exemplarNovo.setQuantidadeDisponivel(exemplarNovo.getQuantidadeDisponivel() + 1);
            }

            else if (statusAntigo == StatusEmprestimo.ATRASADO && statusNovo == StatusEmprestimo.DEVOLVIDO) {
                exemplarNovo.setQuantidadeDisponivel(exemplarNovo.getQuantidadeDisponivel() + 1);
            }

            else if (statusAntigo == StatusEmprestimo.DEVOLVIDO &&
                    (statusNovo == StatusEmprestimo.PENDENTE || statusNovo == StatusEmprestimo.ATRASADO)) {
                exemplarNovo.setQuantidadeDisponivel(exemplarNovo.getQuantidadeDisponivel() - 1);
            }
        }

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
