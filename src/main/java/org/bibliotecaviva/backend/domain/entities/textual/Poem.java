package org.bibliotecaviva.backend.domain.entities.textual;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Poem")
@SuperBuilder
public class Poem extends TextualWork {

    private String rhymeScheme;
    private String poemType;
}
