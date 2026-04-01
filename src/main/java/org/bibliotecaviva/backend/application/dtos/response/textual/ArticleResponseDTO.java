package org.bibliotecaviva.backend.application.dtos.response.textual;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bibliotecaviva.backend.application.dtos.response.TextualWorkResponseDTO;
import org.bibliotecaviva.backend.application.dtos.response.WorkResponseDTO;

@Schema(name = "ArticleResponseDTO")
@Getter
@NoArgsConstructor
@SuperBuilder
public class ArticleResponseDTO extends TextualWorkResponseDTO {
}
