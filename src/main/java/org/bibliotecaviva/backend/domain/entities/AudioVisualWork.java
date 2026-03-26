package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AudioVisualWork extends Work {
    private String url;
}
