package org.bibliotecaviva.backend.application.dtos.response;

import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bibliotecaviva.backend.infrastructure.persistance.converter.DurationConverter;

import java.time.Duration;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
    public abstract class VisualWorkResponseDTO extends WorkResponseDTO {
    private String url;
}
