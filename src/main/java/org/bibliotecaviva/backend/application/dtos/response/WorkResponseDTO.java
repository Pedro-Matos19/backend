package org.bibliotecaviva.backend.application.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.bibliotecaviva.backend.application.dtos.request.audiovisual.LibraLiteratureRequestDTO;
import org.bibliotecaviva.backend.application.dtos.request.audiovisual.MultimediaRequestDTO;
import org.bibliotecaviva.backend.application.dtos.response.audiovisual.LibraLiteratureResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.audiovisual.MultimediaResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.textual.CordelResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.textual.EssayResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.textual.ShortStoryResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.textual.TaleResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.visual.ArtResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.visual.InfographicReponseDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(
        name = "WorkResponseDTO",
        description = "DTO with generic data for works",
        oneOf = { LibraLiteratureResponseDTO.class, MultimediaResponseDTO.class, ArtResponseDTO.class,
                CordelResponseDTO.class, EssayResponseDTO.class, ShortStoryResponseDTO.class, TaleResponseDTO.class ,
                ArtResponseDTO.class, InfographicReponseDTO.class },
        discriminatorProperty = "type"
)
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
    private String type;
//        Todo:
//        Integer viewCount;
//        Integer likeCount;

}
