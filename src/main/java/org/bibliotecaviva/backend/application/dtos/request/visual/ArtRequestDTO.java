package org.bibliotecaviva.backend.application.dtos.request.visual;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

import org.bibliotecaviva.backend.application.dtos.request.WorkRequest;

/**
 * DTO for {@link org.bibliotecaviva.backend.domain.entities.visual.Art}
 */
public record ArtRequestDTO(String title, String author, LocalDateTime publicationDate, String description, String url)
        implements WorkRequest {
}