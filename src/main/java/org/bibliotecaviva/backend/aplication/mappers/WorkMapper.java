package org.bibliotecaviva.backend.aplication.mappers;


import org.bibliotecaviva.backend.aplication.dtos.response.WorkResponseDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.audiovisual.LibraLiteratureResponseDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.audiovisual.MultimediaResponseDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.textual.*;
import org.bibliotecaviva.backend.aplication.dtos.response.visual.ArtResponseDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.visual.InfographicReponseDTO;
import org.bibliotecaviva.backend.domain.entities.Work;
import org.bibliotecaviva.backend.domain.entities.WorkSummary;
import org.bibliotecaviva.backend.domain.entities.audiovisual.LibraLiterature;
import org.bibliotecaviva.backend.domain.entities.audiovisual.Multimedia;
import org.bibliotecaviva.backend.domain.entities.textual.*;
import org.bibliotecaviva.backend.domain.entities.visual.Art;
import org.bibliotecaviva.backend.domain.entities.visual.Infographic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkMapper {

    WorkResponseDTO toWorkDTO(WorkSummary summary);
    default WorkResponseDTO toDTO(Work work) {
        return switch (work) {
            case LibraLiterature w -> toLibraLiteratureResponseDTO(w);
            case Multimedia w -> toMultimediaResponseDTO(w);
            case Article w -> toArticleResponseDTO(w);
            case Cordel w -> toCordelResponseDTO(w);
            case Essay w -> toEssayResponseDTO(w);
            case ShortStory w -> toShortStoryResponseDTO(w);
            case Tale w -> toTaleResponseDTO(w);
            case Art w -> toArtResponseDTO(w);
            case Infographic w -> toInfographicReponseDTO(w);
            default -> throw new IllegalArgumentException(
                    "Tipo de Work não mapeado: " + work.getClass().getSimpleName()
            );
        };
    }

        // mapeamentos específicos de cada entidade
        LibraLiteratureResponseDTO toLibraLiteratureResponseDTO (LibraLiterature libraLiterature);
        MultimediaResponseDTO toMultimediaResponseDTO (Multimedia Multimedia);
        ArticleResponseDTO toArticleResponseDTO (org.bibliotecaviva.backend.domain.entities.textual.Article article);
        CordelResponseDTO toCordelResponseDTO (Cordel cordel);
        EssayResponseDTO toEssayResponseDTO (Essay essay);
        ShortStoryResponseDTO toShortStoryResponseDTO (ShortStory shortStory);
        TaleResponseDTO toTaleResponseDTO (Tale tale);
        ArtResponseDTO toArtResponseDTO (Art art);
        InfographicReponseDTO toInfographicReponseDTO (Infographic infographic);

    }
