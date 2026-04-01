package org.bibliotecaviva.backend.application.dtos.response;

import org.bibliotecaviva.backend.domain.enums.Role;

public record LoginResponseDTO(String token, String email, Role role) {}
