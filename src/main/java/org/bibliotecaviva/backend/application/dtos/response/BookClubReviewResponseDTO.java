package org.bibliotecaviva.backend.application.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookClubReviewResponseDTO(
        UUID id,
        String content,
        String authorName,
        LocalDateTime createdAt,
        BigDecimal rating) {
}
