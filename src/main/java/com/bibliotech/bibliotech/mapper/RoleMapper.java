package com.bibliotech.bibliotech.mapper;

import com.bibliotech.bibliotech.entity.Role;
import com.bibliotech.bibliotech.entity.dto.RoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO toDTO(Role role);

    Role toEntity(RoleDTO roleDTO);

    List<RoleDTO> toDTOList(List<Role> roles);
}