package org.bibliotecaviva.backend.domain.entities.textual;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.bibliotecaviva.backend.domain.entities.TextualWork;

@Entity
/*
* Redação nota 10
*/

public class Essay extends TextualWork {
    private Integer rate;
    private String theme;
    @Column(columnDefinition = "TEXT") //tema com leve contextuializacao
    private String themeDescription;
    @Column(columnDefinition = "TEXT")
    private String feedback;
}
