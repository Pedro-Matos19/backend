package org.bibliotecaviva.backend.application.dtos.request.textual;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.bibliotecaviva.backend.application.dtos.request.WorkRequest;

/**
 * DTO for {@link org.bibliotecaviva.backend.domain.entities.textual.Essay}
 */
public record EssayRequestDTO(String title, String author, LocalDateTime publicationDate, String description,
                              String content, Integer rate, String theme, String themeDescription,
                              String feedback) implements WorkRequest {
}