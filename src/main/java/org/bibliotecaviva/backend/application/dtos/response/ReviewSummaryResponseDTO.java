package org.bibliotecaviva.backend.application.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewSummaryResponseDTO(
        UUID id,
        String content,
        LocalDateTime createdAt,
        BigDecimal rating,
        String userName,
        String userId,
        String bookClubTitle,
        String bookClubId
) {
}
