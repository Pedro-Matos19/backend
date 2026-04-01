package org.bibliotecaviva.backend.application.dtos.request.textual;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.bibliotecaviva.backend.application.dtos.request.WorkRequest;

/**
 * DTO for {@link org.bibliotecaviva.backend.domain.entities.textual.Tale}
 */
public record TaleRequestDTO(String title, String author, LocalDateTime publicationDate, String description,
                             String content, String genre) implements WorkRequest {
}