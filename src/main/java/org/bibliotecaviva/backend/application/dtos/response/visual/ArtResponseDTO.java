package org.bibliotecaviva.backend.application.dtos.response.visual;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bibliotecaviva.backend.application.dtos.response.VisualWorkResponseDTO;

@Schema(name = "ArtResponseDTO")
@Getter
@NoArgsConstructor
@SuperBuilder
public class ArtResponseDTO extends VisualWorkResponseDTO {
}
