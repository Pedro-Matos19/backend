package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="Obras")
public abstract class Work {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    //pósteriormente o usuario
    private String author;
    private LocalDate publicationDate;
    @Column(columnDefinition = "TEXT")
    private String description;
}
