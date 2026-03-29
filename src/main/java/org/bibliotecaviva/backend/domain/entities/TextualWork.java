package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
* contos cordeis e cronicas
 */
@MappedSuperclass
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TextualWork extends Work{
    private String content;
}
