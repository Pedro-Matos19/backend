package org.bibliotecaviva.backend.domain.entities.textual;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.bibliotecaviva.backend.domain.entities.TextualWork;

@Entity
public class Cordel extends TextualWork {

    private String rhymeScheme;
    //ilistracao como ta no banco colocar dps
}
