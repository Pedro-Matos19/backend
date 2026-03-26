package org.bibliotecaviva.backend.domain.entities.textual;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.bibliotecaviva.backend.domain.entities.TextualWork;

@Entity

public class Tale extends TextualWork {
    @Column(columnDefinition = "TEXT")
    private String genre;
}
