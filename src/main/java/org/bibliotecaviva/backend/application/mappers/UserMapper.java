package org.bibliotecaviva.backend.application.mappers;

import org.bibliotecaviva.backend.application.dtos.response.UserResponseDTO;
import org.bibliotecaviva.backend.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponseDTO toDto(User user);

}