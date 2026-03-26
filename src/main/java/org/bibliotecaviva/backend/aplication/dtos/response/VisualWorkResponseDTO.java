package org.bibliotecaviva.backend.aplication.dtos.response;

import jakarta.persistence.Convert;
import org.bibliotecaviva.backend.infrastructure.persistance.converter.DurationConverter;

import java.time.Duration;

public class VisualWorkResponseDTO extends WorkResponseDTO {
    private String url;
    @Convert(converter = DurationConverter.class)
    private Duration duration;
}
