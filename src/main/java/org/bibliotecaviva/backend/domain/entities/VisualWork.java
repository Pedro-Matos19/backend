package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bibliotecaviva.backend.infrastructure.persistance.converter.DurationConverter;

import java.time.Duration;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class  VisualWork extends Work {
    private String url;
    @Convert(converter = DurationConverter.class)
    private Duration duration;
}
