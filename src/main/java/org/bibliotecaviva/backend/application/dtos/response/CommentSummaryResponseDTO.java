package org.bibliotecaviva.backend.application.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentSummaryResponseDTO(
        UUID id,
        String content,
        String userName,
        String workTitle,
        LocalDateTime createdAt
) {
}
