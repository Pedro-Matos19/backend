package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name="Obras")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Work {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    //TODO: relacionamento com usuario
    private String author;
    private LocalDateTime publicationDate;
    @Column(columnDefinition = "TEXT")
    private String description;

    public String getType() {
        return this.getClass().getAnnotation(DiscriminatorValue.class) != null
                ? this.getClass().getAnnotation(DiscriminatorValue.class).value()
                : this.getClass().getSimpleName().toUpperCase();
    }
}
