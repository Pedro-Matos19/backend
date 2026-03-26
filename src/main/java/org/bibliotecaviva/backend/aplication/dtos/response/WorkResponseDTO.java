package org.bibliotecaviva.backend.aplication.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
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
