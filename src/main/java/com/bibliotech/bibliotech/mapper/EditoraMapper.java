package com.bibliotech.bibliotech.mapper;

import com.bibliotech.bibliotech.entity.Editora;
import com.bibliotech.bibliotech.entity.dto.EditoraDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EditoraMapper {

    EditoraDTO toDTO(Editora editora);

    Editora toEntity(EditoraDTO editoraDTO);

    List<EditoraDTO> toDTOList(List<Editora> editoras);
}