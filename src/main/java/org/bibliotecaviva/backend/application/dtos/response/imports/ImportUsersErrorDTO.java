package org.bibliotecaviva.backend.application.dtos.response.imports;

public record ImportUsersErrorDTO(
        int row,
        String email,
        String message
) {}
