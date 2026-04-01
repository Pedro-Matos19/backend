package org.bibliotecaviva.backend.application.dtos.response.textual;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bibliotecaviva.backend.application.dtos.response.TextualWorkResponseDTO;

@Schema(name = "EssayResponseDTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EssayResponseDTO extends TextualWorkResponseDTO {
    private Integer rate;
    private String theme;
    private String themeDescription;
    private String feedback;
}
