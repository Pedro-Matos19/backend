package org.bibliotecaviva.backend.aplication.dtos.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@SuperBuilder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WorkResponseDTO {
    private UUID id;
    private String title;
    private String author;
    private LocalDateTime publicationDate;
    private String description;
//        Todo:
//        Integer viewCount;
//        Integer likeCount;

}
