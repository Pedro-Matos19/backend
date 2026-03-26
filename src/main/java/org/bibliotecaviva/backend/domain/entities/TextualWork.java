package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.MappedSuperclass;

/*
* contos cordeis e cronicas
 */
@MappedSuperclass
public abstract class TextualWork extends Work{
    private String content;
}
