package com.bibliotech.bibliotech.mapper;

import com.bibliotech.bibliotech.entity.Exemplar;
import com.bibliotech.bibliotech.entity.dto.ExemplarDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExemplarMapper {

    ExemplarDTO toDTO(Exemplar exemplar);

    Exemplar toEntity(ExemplarDTO exemplarDTO);

    List<ExemplarDTO> toDTOList(List<Exemplar> exemplares);
}