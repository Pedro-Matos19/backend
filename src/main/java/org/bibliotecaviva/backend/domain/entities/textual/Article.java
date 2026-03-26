package org.bibliotecaviva.backend.domain.entities.textual;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.bibliotecaviva.backend.domain.entities.TextualWork;

@Entity
//armazena os artigos e reportagens

public class Article extends TextualWork {
}
