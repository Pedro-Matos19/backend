package org.bibliotecaviva.backend.application.dtos.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookClubResponseDTO(
        UUID id,
        String organizerName,
        String bookName,
        String bookSynopses,
        String bookAuthor,
        LocalDateTime date,
        String location,
        Long participantsCount,
        String bookCoverUrl,
        BigDecimal averageRating
) {

}
