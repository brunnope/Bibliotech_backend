package com.bibliotech.bibliotech.mapper;

import com.bibliotech.bibliotech.entity.Emprestimo;
import com.bibliotech.bibliotech.entity.dto.EmprestimoDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    EmprestimoDTO toDTO(Emprestimo emprestimo);

    Emprestimo toEntity(EmprestimoDTO emprestimoDTO);

    List<EmprestimoDTO> toDTOList(List<Emprestimo> emprestimos);
}