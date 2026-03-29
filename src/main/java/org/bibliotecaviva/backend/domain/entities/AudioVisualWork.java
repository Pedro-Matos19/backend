package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AudioVisualWork extends Work {
    private String url;
}
