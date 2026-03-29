package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Obras")
@Getter
    @NoArgsConstructor
@AllArgsConstructor
public abstract class Work {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    //pósteriormente o usuario
    private String author;
    private LocalDateTime publicationDate;
    @Column(columnDefinition = "TEXT")
    private String description;
}
