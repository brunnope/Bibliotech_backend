package com.bibliotech.bibliotech.mapper;

import com.bibliotech.bibliotech.entity.dto.LivroDTO;
import com.bibliotech.bibliotech.entity.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LivroMapper {

    @Mapping(source = "idLivro", target = "idLivro")
    LivroDTO toDTO(Livro livro);

    @Mapping(source = "idLivro", target = "idLivro")
    Livro toEntity(LivroDTO livroDTO);

    List<LivroDTO> toDTOList(List<Livro> livros);

    List<Livro> toEntityList(List<LivroDTO> livroDTOs);
}