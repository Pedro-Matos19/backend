package org.bibliotecaviva.backend.aplication.mappers;


import org.bibliotecaviva.backend.aplication.dtos.response.*;
import org.bibliotecaviva.backend.aplication.dtos.response.audiovisual.LibraLiteratureResponseDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.audiovisual.MultimediaResponseDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.textual.*;
import org.bibliotecaviva.backend.aplication.dtos.response.visual.ArtResponseDTO;
import org.bibliotecaviva.backend.aplication.dtos.response.visual.InfographicReponseDTO;
import org.bibliotecaviva.backend.domain.entities.Work;
import org.bibliotecaviva.backend.domain.entities.audiovisual.LibraLiterature;
import org.bibliotecaviva.backend.domain.entities.audiovisual.Multimedia;
import org.bibliotecaviva.backend.domain.entities.textual.*;
import org.bibliotecaviva.backend.domain.entities.visual.Art;
import org.bibliotecaviva.backend.domain.entities.visual.Infographic;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;
//
//@Mapper(componentModel = "spring")
//public interface WorkMapper {
//
//    @SubclassMapping(source = LibraLiterature.class, target = LibraLiteratureResponseDTO.class)
//    @SubclassMapping(source = Multimedia.class, target = MultimediaResponseDTO.class)
//    @SubclassMapping(source = org.bibliotecaviva.backend.domain.entities.textual.Article.class, target = ArticleResponseDTO.class)
//    @SubclassMapping(source = Cordel.class, target = CordelResponseDTO.class)
//    @SubclassMapping(source = Essay.class, target = EssayResponseDTO.class)
//    @SubclassMapping(source = ShortStory.class, target = ShortStoryResponseDTO.class)
//    @SubclassMapping(source = Tale.class, target = TaleResponseDTO.class)
//    @SubclassMapping(source = Art.class, target = ArtResponseDTO.class)
//    @SubclassMapping(source = Infographic.class, target = InfographicReponseDTO.class)
//    WorkResponseDTO toResponseDTO(Work work);
//
//    // mapeamentos específicos de cada entidade
//    LibraLiteratureResponseDTO toLibraLiteratureResponseDTO(LibraLiterature libraLiterature);
//    MultimediaResponseDTO toMultimediaResponseDTO(Multimedia Multimedia);
//    ArticleResponseDTO toAricleResponseDTO(org.bibliotecaviva.backend.domain.entities.textual.Article article);
//    CordelResponseDTO toCordelResponseDTO(Cordel cordel);
//    EssayResponseDTO toEssayResponseDTO(Essay essay);
//    ShortStoryResponseDTO toShortStoryResponseDTO(ShortStory shortStory);
//    ArtResponseDTO toArtResponseDTO(Art art);
//    InfographicReponseDTO toInfographicReponseDTO(Infographic infographic);
//
//}
