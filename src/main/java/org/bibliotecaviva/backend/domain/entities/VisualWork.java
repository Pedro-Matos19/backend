package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import org.bibliotecaviva.backend.infrastructure.persistance.converter.DurationConverter;

import java.time.Duration;

@MappedSuperclass
public class VisualWork extends Work {
    private String url;
    @Convert(converter = DurationConverter.class)
    private Duration duration;
}
