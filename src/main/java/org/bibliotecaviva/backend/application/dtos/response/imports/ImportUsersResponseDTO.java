package org.bibliotecaviva.backend.application.dtos.response.imports;

import java.util.List;

public record ImportUsersResponseDTO(
        int totalRows,
        int createdCount,
        int failedCount,
        List<ImportUsersErrorDTO> erros
) {}
