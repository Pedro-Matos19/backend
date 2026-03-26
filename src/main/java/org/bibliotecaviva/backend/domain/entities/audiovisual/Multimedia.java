package org.bibliotecaviva.backend.domain.entities.audiovisual;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.bibliotecaviva.backend.domain.entities.AudioVisualWork;

@Entity
/*
aqui tambem tem diversas subcategorias  produções audiovisuais feitas por alunos
(curtas-metragens, vídeos-poemas, entrevistas)
 */
public class Multimedia extends AudioVisualWork {

}
