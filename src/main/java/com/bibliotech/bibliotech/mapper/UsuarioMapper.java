package com.bibliotech.bibliotech.mapper;

import com.bibliotech.bibliotech.entity.Usuario;
import com.bibliotech.bibliotech.entity.dto.UsuarioComSenhaDTO;
import com.bibliotech.bibliotech.entity.dto.UsuarioDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioDTO toDTO(Usuario usuario);

    UsuarioComSenhaDTO toEntityDTOComSenha(Usuario usuario);

    List<UsuarioDTO> toDTOList(List<Usuario> usuarios);

    Usuario toEntity(UsuarioDTO usuarioDTO);

    Usuario toEntity(UsuarioComSenhaDTO usuarioComSenhaDTO);
}