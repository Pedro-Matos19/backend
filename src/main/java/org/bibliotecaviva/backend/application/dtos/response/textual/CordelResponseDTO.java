package org.bibliotecaviva.backend.application.dtos.response.textual;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bibliotecaviva.backend.application.dtos.response.TextualWorkResponseDTO;

@Schema(name = "CordelResponseDTO")
@Getter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class CordelResponseDTO extends TextualWorkResponseDTO {
    private String rhymeScheme;

}
