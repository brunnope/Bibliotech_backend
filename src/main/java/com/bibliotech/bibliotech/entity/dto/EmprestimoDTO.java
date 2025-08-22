package com.bibliotech.bibliotech.entity.dto;

import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.entity.enums.StatusEmprestimo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class EmprestimoDTO {
    private Long idEmprestimo;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;
    private UsuarioDTO usuario;
    private ExemplarDTO exemplar;


    public Long getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(Long idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public ExemplarDTO getExemplar() {
        return exemplar;
    }

    public void setExemplar(ExemplarDTO exemplar) {
        this.exemplar = exemplar;
    }
}