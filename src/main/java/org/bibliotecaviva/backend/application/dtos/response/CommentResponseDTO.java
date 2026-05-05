package org.bibliotecaviva.backend.application.dtos.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDTO(
        UUID id,
        String content,
        String authorName,
        LocalDateTime createdAt,
        Long likes
) {
}
