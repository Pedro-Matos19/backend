package org.bibliotecaviva.backend.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "Obras")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User author;
    private String authorName;
    private LocalDateTime publicationDate;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Long viewCount = 0L;
    private String studentClass;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "work")
    private List<Comment> comments;

    public String getType() {
        return this.getClass().getAnnotation(DiscriminatorValue.class) != null
                ? this.getClass().getAnnotation(DiscriminatorValue.class).value()
                : this.getClass().getSimpleName().toUpperCase();
    }
    public String resolveAuthorName(){
        return author != null ? author.getName() : authorName;
    }
}
